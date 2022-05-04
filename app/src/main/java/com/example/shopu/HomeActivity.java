package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.shopu.adapters.EstablishMentAdapter;
import com.example.shopu.model.Establishment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ListView gvwEstablishments;
    EstablishMentAdapter estAdapter;
    ArrayList<Establishment> establishments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        gvwEstablishments = findViewById(R.id.gvwEstablishments);
        System.out.println("ACAA1");
        establishments = new ArrayList<Establishment>();
        System.out.println("ACAA2");
        estAdapter = new EstablishMentAdapter(this,establishments);
        System.out.println("ACAA3");

        if(estAdapter != null){
            gvwEstablishments.setAdapter(estAdapter);
        }

        System.out.println("ACAA4");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }


}