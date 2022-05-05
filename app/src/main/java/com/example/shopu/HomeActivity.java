package com.example.shopu;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.model.Establishment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private GridView gvwEstablishments;
    private EstablishmentAdapter estAdapter;
    private ArrayList<Establishment> establishments;
    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityResultLauncher<String> getSinglePermissionLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        requestLocationAccessPermission();

        findLocation();

    }

    public void requestLocationAccessPermission() {
        getSinglePermissionLocation = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result == true) {

                            loadEstablishments();
                            gvwEstablishments = findViewById(R.id.gvwEstablishments);
                            estAdapter = new EstablishmentAdapter(getApplicationContext(),establishments);
                            gvwEstablishments.setAdapter(estAdapter);

                        } else {
                            Toast.makeText(getApplicationContext(), "No location, no app. Bitch", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        getSinglePermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void findLocation(){

    }

    public void loadEstablishments(){

        this.establishments = new ArrayList<>();

        Establishment e1 = new Establishment(); e1.setName("Establecimiento 1");
        Establishment e2 = new Establishment(); e2.setName("Establecimiento 2");
        Establishment e3 = new Establishment(); e3.setName("Establecimiento 3");
        Establishment e4 = new Establishment(); e4.setName("Establecimiento 4");
        Establishment e5 = new Establishment(); e5.setName("Establecimiento 5");
        Establishment e6 = new Establishment(); e6.setName("Establecimiento 6");

        establishments.add(e1);
        establishments.add(e2);
        establishments.add(e3);
        establishments.add(e4);
        establishments.add(e5);
        establishments.add(e6);
        establishments.add(e1);
        establishments.add(e2);
        establishments.add(e3);
        establishments.add(e4);
        establishments.add(e5);
        establishments.add(e5);

    }

}