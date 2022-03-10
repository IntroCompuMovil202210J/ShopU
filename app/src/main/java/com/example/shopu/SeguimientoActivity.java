package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SeguimientoActivity extends AppCompatActivity {

    ImageButton ibntEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);

        ibntEstado = findViewById(R.id.ibtnEstado);




    }
    @Override
    protected void onResume(){
        super.onResume();

        ibntEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(view.getContext(),SeguimientoInfoActivity.class);
                startActivity(info);
            }
        });

    }
}