package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LogScreenActivity extends AppCompatActivity {

    EditText correo, contrasena;
    Button btniniciarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);

        correo = findViewById(R.id.etxtmail);
        contrasena = findViewById(R.id.etxtpassword);
        btniniciarse = findViewById(R.id.btniniciarse);
    }
}