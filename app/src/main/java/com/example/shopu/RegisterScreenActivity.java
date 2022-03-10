package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterScreenActivity extends AppCompatActivity {

    EditText etxtname, etxtlastname, etxtmailregister, etxtpasswordregister, etxtconfirmpassword, etxtphone;
    CheckBox checkTerms;
    Button btnSignin;
    Toast noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_screen);

        etxtname = findViewById(R.id.etxtname);
        etxtlastname = findViewById(R.id.etxtlastname);
        etxtmailregister = findViewById(R.id.etxtmailregister);
        etxtpasswordregister = findViewById(R.id.etxtpasswordregister);
        etxtconfirmpassword = findViewById(R.id.etxtconfirmpassword);
        etxtphone = findViewById(R.id.etxtphone);
        checkTerms = findViewById(R.id.checkTerms);
        btnSignin = findViewById(R.id.btnSiginScreen);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrar = new Intent(view.getContext(),LogScreenActivity.class);
                noti = Toast.makeText(view.getContext(),"Registro Exitoso",Toast.LENGTH_LONG);
                noti.show();
                startActivity(registrar);

            }
        });
    }
}