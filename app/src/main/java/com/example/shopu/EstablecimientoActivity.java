package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EstablecimientoActivity extends AppCompatActivity {

    ImageButton ibnVerRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento);

        ibnVerRuta = findViewById(R.id.ibnVerRuta);

        ibnVerRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent log = new Intent(view.getContext(),SeguimientoActivity.class);
                startActivity(log);

            }
        });
    }
}