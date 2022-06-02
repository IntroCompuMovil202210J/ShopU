package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shopu.model.DeliveryMan;
import com.example.shopu.model.Order;
import com.example.shopu.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class DeliveryTrackOrderActivity extends AppCompatActivity {
    private final double RADIUS_OF_EARTH_KM = 6371.01;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Double currentUserLatitude = 0d, currentUserLongitude = 0d, establishmentLatitude = 0d, establishmentLongitude = 0d;
    MapView map;
    String orderId;
    DeliveryMan currentUser;
    Marker currentUserMarker, establishmentMarker;
    GeoPoint userPoint, establishmentPoint;
    RoadManager roadManager;
    Polyline roadOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_track_order);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = notifyCallbackChanges();
        locationRequest = createLocationRequest();
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        roadManager = new OSRMRoadManager(this, "ANDROID");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        orderId = getIntent().getStringExtra("order");
        getOrderEstablishmentLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private LocationCallback notifyCallbackChanges() {
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    if (currentUserMarker == null) {
                        userPoint = new GeoPoint(currentUserLatitude, currentUserLongitude);
                        currentUserMarker = createMarker(
                                userPoint,
                                "Current Location",
                                "Current Location",
                                R.drawable.ic_
                        );
                        placeMarker(currentUserMarker);
                        drawRoute(new GeoPoint(currentUserLatitude, currentUserLongitude), establishmentPoint);
                    } else {
                        currentUserMarker.setPosition(new GeoPoint(currentUserLatitude, currentUserLongitude));
                        setCurrentUser();
                        drawRoute(new GeoPoint(currentUserLatitude, currentUserLongitude), establishmentPoint);
                    }
                }
            }
        };
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


    private void setCurrentUser() {
        DatabaseReference ref = database.getReference("users").child(auth.getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getKey().equals(auth.getCurrentUser().getUid())) {
                    currentUser = snapshot.getValue(DeliveryMan.class);
                    currentUserLatitude = currentUser.getLatitude();
                    currentUserLongitude = currentUser.getLongitude();
                    IMapController mapController = map.getController();
                    mapController.setZoom(13.0);
                    mapController.setCenter(new GeoPoint(currentUserLatitude, currentUserLongitude));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private Marker createMarker(GeoPoint p, String title, String desc, int iconID) {
        Marker marker = null;
        if (map != null) {
            marker = new Marker(map);
            if (title != null) marker.setTitle(title);
            if (desc != null) marker.setSubDescription(desc);
            if (iconID != 0) {
                Drawable myIcon = getResources().getDrawable(iconID, this.getTheme());
                marker.setIcon(myIcon);
            }
            marker.setPosition(p);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        }
        return marker;
    }

    private void placeMarker(Marker marker) {
        map.getOverlays().add(marker);
    }

    public double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }

    private void moveMainMarker(GeoPoint p) {
        if (currentUserMarker != null) {
            map.getOverlays().remove(currentUserMarker);
        }
        currentUserMarker = createMarker(p, "location", null, R.drawable.ic_location);
        map.getOverlays().add(currentUserMarker);
    }

    private void drawRoute(GeoPoint start, GeoPoint finish){
        ArrayList<GeoPoint> routePoints = new ArrayList<>();
        routePoints.add(start);
        routePoints.add(finish);
        Road road = roadManager.getRoad(routePoints);
        if(map!=null){
            if(roadOverlay!=null){
                map.getOverlays().remove(roadOverlay);
            }
            roadOverlay = RoadManager.buildRoadOverlay(road);
            roadOverlay.getOutlinePaint().setColor(Color.MAGENTA);
            roadOverlay.getOutlinePaint().setStrokeWidth(10);
            map.getOverlays().add(roadOverlay);
        }
    }

    private void getOrderEstablishmentLocation() {
        DatabaseReference ref = database.getReference("orders").child(orderId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getKey().equals(orderId)) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null) {
                        establishmentLatitude = order.getLatitude();
                        establishmentLongitude = order.getLongitude();
                        establishmentPoint = new GeoPoint(establishmentLatitude, establishmentLongitude);
                        establishmentMarker = createMarker(establishmentPoint, "Establishment", "Establishment", R.drawable.ic_baseline_fastfood_24);
                        placeMarker(establishmentMarker);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}