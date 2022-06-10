package com.example.shopu;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.model.Cart;
import com.example.shopu.model.Establishment;
import com.example.shopu.model.EstablishmentCategory;
import com.example.shopu.model.Product;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private GridView gvwEstablishments;
    private TextView txtAddress;
    private Button pharmacy,stationery,feeding,all;
    private EditText textSearch;
    private BottomNavigationView menu;

    private EstablishmentAdapter estAdapter;
    private ArrayList<Establishment> establishments;
    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityResultLauncher<String> getSinglePermissionLocation;
    Geocoder mGeocoder;

    private Double latitude = 0d;
    private Double longitude = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtAddress = findViewById(R.id.txtDireccion);
        gvwEstablishments = findViewById(R.id.gvwEstablishments);

        pharmacy = findViewById(R.id.btnFarmacia);
        stationery = findViewById(R.id.btnPapeleria);
        feeding = findViewById(R.id.btnAlimentacion);
        all = findViewById(R.id.btnAll);

        textSearch = findViewById(R.id.etxtBuscar);

        menu = findViewById(R.id.navigation);

        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estAdapter = new EstablishmentAdapter(getApplicationContext(),filterEstablishments(establishments,EstablishmentCategory.PHARMACY));
                gvwEstablishments.setAdapter(estAdapter);
            }

        });

        feeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estAdapter = new EstablishmentAdapter(getApplicationContext(),filterEstablishments(establishments,EstablishmentCategory.FEEDING));
                gvwEstablishments.setAdapter(estAdapter);

            }
        });

        stationery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estAdapter = new EstablishmentAdapter(getApplicationContext(),filterEstablishments(establishments,EstablishmentCategory.STATIONERY));
                gvwEstablishments.setAdapter(estAdapter);

            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                estAdapter = new EstablishmentAdapter(getApplicationContext(),establishments);
                gvwEstablishments.setAdapter(estAdapter);
            }
        });

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemClicked = item.getItemId();

                switch (itemClicked){

                    case R.id.search:
                        String name = textSearch.getText().toString();
                        estAdapter = new EstablishmentAdapter(getApplicationContext(),filterEstablishments(establishments,name));
                        gvwEstablishments.setAdapter(estAdapter);

                    case R.id.user:
                        startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
                    case R.id.car:
                        Intent i = new Intent(HomeActivity.this, CartActivity.class);
                        i.putExtra("latitude", latitude);
                        i.putExtra("longitude", longitude);
                        startActivity(i);
                }
                return false;
            }
        });


        setEditorListener();

        requestLocationAccessPermission();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                            estAdapter = new EstablishmentAdapter(getApplicationContext(),establishments);
                            gvwEstablishments.setAdapter(estAdapter);

                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor da acceso a la localizacion", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        getSinglePermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);

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
                                writeLocation();
                            }
                        }
                    });
        }else{
            txtAddress.setText("Universidad Javeriana");
        }

    }

    public void writeLocation(){
        if (longitude!= 0 || latitude != 0) {

            try {
                List<Address> addresses = mGeocoder.getFromLocation(latitude,longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    txtAddress.setText(addressResult.getAddressLine(0));

                } else {Toast.makeText(getApplicationContext(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setEditorListener(){

        textSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                String name;
                if (i == EditorInfo.IME_ACTION_SEND) {
                   name = textSearch.getText().toString();
                    if (!name.isEmpty()) {

                        estAdapter = new EstablishmentAdapter(getApplicationContext(),filterEstablishments(establishments,name));
                        gvwEstablishments.setAdapter(estAdapter);

                    } else {Toast.makeText(getApplicationContext(), "La busqueda esta vacia", Toast.LENGTH_SHORT).show();}

                }

                return false;
            };
        });

    };



    public void loadEstablishments(){

        this.establishments = new ArrayList<>();

        Establishment e1 = new Establishment(); e1.setName("El corral"); e1.setCategory(EstablishmentCategory.FEEDING);
        Establishment e2 = new Establishment(); e2.setName("Drogas la rebaja"); e2.setCategory(EstablishmentCategory.PHARMACY);
        Establishment e3 = new Establishment(); e3.setName("Comercial Papelera"); e3.setCategory(EstablishmentCategory.STATIONERY);
        Establishment e4 = new Establishment(); e4.setName("Sierra Nevada"); e4.setCategory(EstablishmentCategory.FEEDING);
        Establishment e5 = new Establishment(); e5.setName("Farmatodo"); e5.setCategory(EstablishmentCategory.PHARMACY);
        Establishment e6 = new Establishment(); e6.setName("El triangulo"); e6.setCategory(EstablishmentCategory.STATIONERY);

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

    public ArrayList<Establishment> filterEstablishments(List<Establishment> establishments, EstablishmentCategory category){

        ArrayList<Establishment> toReturn = new ArrayList<>();

        for(Establishment e : establishments){

            if(e.getCategory() == category){
                toReturn.add(e);
            }

        }

        return toReturn;
    }

    public ArrayList<Establishment> filterEstablishments(List<Establishment> establishments, String filter){

        ArrayList<Establishment> toReturn = new ArrayList<>();

        for(Establishment e : establishments){

            if(e.getName().contains(filter)){
                toReturn.add(e);
            }

        }

        return toReturn;
    }

}