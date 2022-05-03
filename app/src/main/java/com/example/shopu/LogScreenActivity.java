package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    EditText email, password;
    Button btnIniciarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);

        email = findViewById(R.id.etxtmail);
        password = findViewById(R.id.etxtpassword);
        btnIniciarse = findViewById(R.id.btniniciarse);
        mAuth = FirebaseAuth.getInstance();

        btnIniciarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(String.valueOf(email.getText()), String.valueOf(password.getText()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());
    }

    private void signIn(String email, String password) {
        if (validateForm())
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
            email.setText("");
            password.setText("");
        }
    }

    private boolean validateForm() {
        boolean flag = true;
        if (TextUtils.isEmpty(String.valueOf(email.getText()))) {
            email.setError("Requerido");
            flag = false;
        } else
            email.setError(null);
        if (TextUtils.isEmpty(String.valueOf(password.getText()))) {
            password.setError("Requerido");
            flag = false;
        } else
            password.setError(null);
        return flag;
    }
}