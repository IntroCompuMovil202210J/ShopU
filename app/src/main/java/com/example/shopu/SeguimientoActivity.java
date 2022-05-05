package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class SeguimientoActivity extends AppCompatActivity {

    Double longitude;
    Double latitude;
    Marker userMarker, establishmentMarker;
    MapView map;

    GeoPoint userLocation,establishment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        longitude = getIntent().getDoubleExtra("longitude", 0d);
        latitude = getIntent().getDoubleExtra("latitude", 0d);
        userLocation = new GeoPoint(latitude,longitude);

        establishment = new GeoPoint(4.632890, -74.063957);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        userMarker = new Marker(map);
        Drawable userIcon = getResources().getDrawable(R.drawable.ic_, this.getTheme());
        userMarker.setIcon(userIcon);
        userMarker.setPosition(userLocation);
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(userMarker);

        establishmentMarker = new Marker(map);
        Drawable establishmentIcon = getResources().getDrawable(R.drawable.ic_baseline_fastfood_24, this.getTheme());
        establishmentMarker.setIcon(establishmentIcon);
        establishmentMarker.setPosition(establishment);
        map.getOverlays().add(establishmentMarker);

    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);
        mapController.setCenter(this.userLocation);

    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }
}