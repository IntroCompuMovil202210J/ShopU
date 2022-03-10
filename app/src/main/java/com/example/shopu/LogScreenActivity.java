package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogScreenActivity extends AppCompatActivity {

    EditText correo, contrasena;
    Button btnIniciarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);

        correo = findViewById(R.id.etxtmail);
        contrasena = findViewById(R.id.etxtpassword);
        btnIniciarse = findViewById(R.id.btniniciarse);

        btnIniciarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                startActivity(i);
            }
        });
    }
}