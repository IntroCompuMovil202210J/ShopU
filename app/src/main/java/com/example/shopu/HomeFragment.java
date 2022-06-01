package com.example.shopu;

import android.Manifest;
import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.shopu.enums.EstablishmentCategory;
import com.example.shopu.model.Establishment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.osmdroid.config.Configuration;

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

    private EstablishmentAdapter estAdapter;
    private ArrayList<Establishment> establishments;
    private FusedLocationProviderClient mFusedLocationClient;
    private ActivityResultLauncher<String> getSinglePermissionLocation;
    Geocoder mGeocoder;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        requestLocationAccessPermission();
        return root;
    }

    public void onAllClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), establishments);
        gvwEstablishments.setAdapter(estAdapter);
    }

    private void onFeedingClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), filterEstablishments(establishments, EstablishmentCategory.FEEDING));
        gvwEstablishments.setAdapter(estAdapter);
    }

    public void onStationeryClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), filterEstablishments(establishments, EstablishmentCategory.STATIONERY));
        gvwEstablishments.setAdapter(estAdapter);
    }

    public void onPharmacyClicked() {
        estAdapter = new EstablishmentAdapter(root.getContext(), filterEstablishments(establishments, EstablishmentCategory.PHARMACY));
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
                            estAdapter = new EstablishmentAdapter(root.getContext(), establishments);
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