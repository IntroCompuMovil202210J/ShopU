package com.example.shopu;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etxtEmail, etxtPassword;
    Button btnIniciarse;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public static final String PATH_USERS="users/";

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
                if(validateEmail(etxtEmail.getText().toString()) || validatePassword(etxtPassword.getText().toString()))
                    signIn(String.valueOf(etxtEmail.getText()), String.valueOf(etxtPassword.getText()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());

    }

    private boolean validateEmail(String email) {
        boolean flag = true;
        if (email.isEmpty()) {
            etxtEmail.setError("Por favor ingresa un correo");
            etxtEmail.requestFocus();
            flag = false;
        }
        return flag;
    }

    private boolean validatePassword(String password) {
        boolean flag = true;
        if (password.isEmpty()) {
            etxtPassword.setError("Por favor ingresa una contraseña");
            etxtPassword.requestFocus();
            flag = false;
        }
        return flag;
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
        if (user != null) {
            myRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){

                        if(task.getResult().exists()){
                            User usuario = task.getResult().getValue(User.class);

                            if(usuario.getType().equals("deliveryMan"))
                                startActivity(new Intent(LogScreenActivity.this,DeliveryHomeActivity.class));
                            else
                                startActivity(new Intent(LogScreenActivity.this,HomeActivity.class));
                        }
                    }
                }
            });

        } else {
            etxtEmail.setText("");
            etxtPassword.setText("");
        }
    }

}