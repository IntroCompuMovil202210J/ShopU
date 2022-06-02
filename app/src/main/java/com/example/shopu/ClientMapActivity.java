package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shopu.model.Client;
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
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

public class ClientMapActivity extends AppCompatActivity {
    private final double RADIUS_OF_EARTH_KM = 6371.01;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Double currentUserLatitude = 0d, currentUserLongitude = 0d, deliveryLatitude = 0d, deliveryLongitude = 0d;
    MapView map;
    String orderId;
    Client currentUser;
    String deliveryManId;
    Marker currentUserMarker, deliveryManMarker;
    GeoPoint userPoint, deliveryManPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_map);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = notifyCallbackChanges();
        locationRequest = createLocationRequest();
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        orderId = getIntent().getStringExtra("order");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCurrentUser();
        setDeliveryMan();
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
                    } else {
                        currentUserMarker.setPosition(new GeoPoint(currentUserLatitude, currentUserLongitude));
                        setCurrentUser();
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
                    currentUser = snapshot.getValue(Client.class);
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

    private void getDeliveryManLocation() {
        DatabaseReference ref = database.getReference("users").child(deliveryManId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getKey().equals(deliveryManId)) {
                    if (snapshot.getValue() != null) {
                        Log.i("DeliveryMan", snapshot.toString());
                        DeliveryMan deliveryMan = snapshot.getValue(DeliveryMan.class);
                        deliveryLatitude = deliveryMan.getLatitude();
                        deliveryLongitude = deliveryMan.getLongitude();
                        deliveryManPoint = new GeoPoint(deliveryLatitude, deliveryLongitude);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDeliveryMan() {
        DatabaseReference ref = database.getReference("orders").child(orderId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getKey().equals(orderId)) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null) {
                        deliveryManId = order.getDeliveryMan();
                        getDeliveryManLocation();
                        deliveryManPoint = new GeoPoint(deliveryLatitude, deliveryLongitude);
                        deliveryManMarker = createMarker(deliveryManPoint, "Delivery", "Delivery", R.drawable.ic_person_foreground);
                        placeMarker(deliveryManMarker);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}