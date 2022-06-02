package com.example.shopu;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.deliveryFragments.DeliveryProfileFragment;
import com.example.shopu.deliveryFragments.OrderListFragment;
import com.example.shopu.model.Order;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class DeliveryHomeActivity extends AppCompatActivity {

    Button btnProfile;
    Button btnOrder;

    private Double latitude = 0d;
    private Double longitude = 0d;

    private BottomNavigationView menu;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private ActivityResultLauncher<String> getSinglePermissionLocation;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_home);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println( "Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token

                        String token = task.getResult();
                        myRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("Token");
                        myRef.setValue(token);
                        // Log and toast

                        System.out.println("Este es mi pto token: " + token);
                    }
                });

        menu = findViewById(R.id.navigation_menu_delivery);

        database = FirebaseDatabase.getInstance();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();

                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    updateLocation();

                }
            }
        };

        requestLocationAccessPermission();


        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemClicked = item.getItemId();

                switch (itemClicked) {
                    case R.id.ordersList:
                        replaceFragment(new OrderListFragment());
                        break;

                    case R.id.profile:
                        replaceFragment(new DeliveryProfileFragment());
                        break;
                }

                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            mLocationRequest = createLocationRequest();
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        }else{

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private LocationRequest createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(1000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){

            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        }
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void requestLocationAccessPermission() {
        getSinglePermissionLocation = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {

                        } else {
                            Toast.makeText(getApplicationContext(), "No location, no app. Bitch", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        getSinglePermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);

    }
    public void updateLocation(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        myRef = database.getReference("users/"+ user.getUid() + "/longitude");
        myRef.setValue(longitude);

        myRef = database.getReference("users/"+ user.getUid() + "/latitude");
        myRef.setValue(latitude);

    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
}