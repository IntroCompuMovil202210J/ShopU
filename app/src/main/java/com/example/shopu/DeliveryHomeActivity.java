package com.example.shopu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopu.model.Order;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class DeliveryHomeActivity extends AppCompatActivity {

    Button btnProfile;
    Button btnOrder;

    Double latitude;
    Double longitude;

    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityResultLauncher<String> getSinglePermissionLocation;
    Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_home);

        btnProfile = findViewById(R.id.btnProfile);
        btnOrder = findViewById(R.id.btnOrders);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),DeliveryProfileActivity.class));
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), OrdersActivity.class);
                i.putExtra("latitude",latitude);
                i.putExtra("Longitude",longitude);
                startActivity(new Intent(view.getContext(),OrdersActivity.class));
            }
        });


    }

    public void findLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mGeocoder = new Geocoder(getBaseContext());

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();

                            }
                        }
                    });
        }

    }
}