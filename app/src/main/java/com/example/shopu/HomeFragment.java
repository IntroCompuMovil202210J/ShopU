package com.example.shopu;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.clientFragments.EstablishmentFragment;
import com.example.shopu.enums.EstablishmentCategory;
import com.example.shopu.model.Establishment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.osmdroid.config.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    View root;

    private Button all, paper, pharmacy, feeding;
    private GridView gvwEstablishments;
    private TextView txtAddress;
    private EditText textSearch;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private EstablishmentAdapter estAdapter;
    private ArrayList<Establishment> establishments;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private ActivityResultLauncher<String> getSinglePermissionLocation;

    Fragment establishmentFragment;

    private Double latitude = 0d;
    private Double longitude = 0d;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            mLocationRequest = createLocationRequest();
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        }else{

        }

    }

    @Override
    public void onPause() {
        super.onPause();

        stopLocationUpdates();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);


        Context context = getActivity().getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        txtAddress = root.findViewById(R.id.txtDireccion);

        textSearch = root.findViewById(R.id.etxtBuscar);
        all = root.findViewById(R.id.btnAll);
        paper = root.findViewById(R.id.btnPapeleria);
        pharmacy = root.findViewById(R.id.btnFarmacia);
        feeding = root.findViewById(R.id.btnAlimentacion);
        gvwEstablishments = root.findViewById(R.id.gvwEstablishments);

        database = FirebaseDatabase.getInstance();

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAllClicked();
            }
        });
        feeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedingClicked();
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStationeryClicked();
            }
        });
        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPharmacyClicked();
            }
        });

        // location updates //

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();

                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    updateLocation();

//                    try {
//
//                        txtAddress.setText(new Geocoder(getContext()).getFromLocation(latitude,longitude,1).get(0).getAddressLine(0));
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                }
            }
        };

        requestLocationAccessPermission();

        return root;
    }

    private LocationRequest createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(1000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){

            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        }
    }

    private void stopLocationUpdates(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    public void updateLocation(){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        myRef = database.getReference("users/"+ user.getUid() + "/longitude");
        myRef.setValue(longitude);

        myRef = database.getReference("users/"+ user.getUid() + "/latitude");
        myRef.setValue(latitude);

    }

    public void onAllClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), establishments, establishmentFragment);
        gvwEstablishments.setAdapter(estAdapter);
    }

    private void onFeedingClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), filterEstablishments(establishments, EstablishmentCategory.FEEDING), establishmentFragment);
        gvwEstablishments.setAdapter(estAdapter);
    }

    public void onStationeryClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), filterEstablishments(establishments, EstablishmentCategory.STATIONERY), establishmentFragment);
        gvwEstablishments.setAdapter(estAdapter);
    }

    public void onPharmacyClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), filterEstablishments(establishments, EstablishmentCategory.PHARMACY), establishmentFragment);
        gvwEstablishments.setAdapter(estAdapter);
    }

    public ArrayList<Establishment> filterEstablishments(List<Establishment> establishments, EstablishmentCategory category) {

        ArrayList<Establishment> toReturn = new ArrayList<>();

        for (Establishment e : establishments) {

            if (e.getCategory() == category) {
                toReturn.add(e);
            }
        }

        return toReturn;
    }

    public void requestLocationAccessPermission() {
        getSinglePermissionLocation = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {

                            loadEstablishments();
                            estAdapter = new EstablishmentAdapter(root.getContext(), establishments, establishmentFragment);
                            gvwEstablishments.setAdapter(estAdapter);

                        } else {
                            Toast.makeText(root.getContext(), "No location, no app. Bitch", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        getSinglePermissionLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION);

    }

    public void loadEstablishments() {

        this.establishments = new ArrayList<>();

        Establishment e1 = new Establishment();
        e1.setName("El corral");
        e1.setCategory(EstablishmentCategory.FEEDING);
        e1.setScore("3.1");
        Establishment e2 = new Establishment();
        e2.setName("Drogas la rebaja");
        e2.setCategory(EstablishmentCategory.PHARMACY);
        e2.setScore("3.2");
        Establishment e3 = new Establishment();
        e3.setName("Comercial Papelera");
        e3.setCategory(EstablishmentCategory.STATIONERY);
        e3.setScore("4.5");
        Establishment e4 = new Establishment();
        e4.setName("Sierra Nevada");
        e4.setCategory(EstablishmentCategory.FEEDING);
        e4.setScore("4.5");
        Establishment e5 = new Establishment();
        e5.setName("Farmatodo");
        e5.setCategory(EstablishmentCategory.PHARMACY);
        e5.setScore("4.5");
        Establishment e6 = new Establishment();
        e6.setName("El triangulo");
        e6.setCategory(EstablishmentCategory.STATIONERY);
        e6.setScore("4.5");

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