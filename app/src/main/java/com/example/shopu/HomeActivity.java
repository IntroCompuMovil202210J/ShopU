package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.model.Establishment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    GridView gvwEstablishments;
    EstablishmentAdapter estAdapter;
    ArrayList<Establishment> establishments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadEstablishments();
        gvwEstablishments = findViewById(R.id.gvwEstablishments);
        estAdapter = new EstablishmentAdapter(this,establishments);
        gvwEstablishments.setAdapter(estAdapter);

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