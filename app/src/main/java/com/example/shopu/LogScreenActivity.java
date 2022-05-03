package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etxtEmail, etxtPassword;
    Button btnIniciarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);

        etxtEmail = findViewById(R.id.etxtmail);
        etxtPassword = findViewById(R.id.etxtpassword);
        btnIniciarse = findViewById(R.id.btniniciarse);
        mAuth = FirebaseAuth.getInstance();

        btnIniciarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(String.valueOf(etxtEmail.getText()), String.valueOf(etxtPassword.getText()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO Generar sesion para el usuario que ingresa
        updateUI(mAuth.getCurrentUser());
    }

    private void signIn(String email, String password) {
        signInFirebase(email, password);
    }

    private void signInFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    updateUI(mAuth.getCurrentUser());
                else
                    Toast.makeText(LogScreenActivity.this, "Fallo de autenticación", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null)
            startActivity(new Intent(LogScreenActivity.this, HomeActivity.class));
        else {
            etxtEmail.setText("");
            etxtPassword.setText("");
        }
    }

}