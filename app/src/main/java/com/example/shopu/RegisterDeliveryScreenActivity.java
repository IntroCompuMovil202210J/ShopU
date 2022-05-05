package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopu.model.DeliveryMan;
import com.example.shopu.utils.Patterns;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterDeliveryScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText etxtName, etxtLastName, etxtEmail, etxtPassword, etxtConfirmPassword, etxtPhone;
    CheckBox checkTerms;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etxtName = findViewById(R.id.etxtName);
        etxtLastName = findViewById(R.id.etxtLastName);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtConfirmPassword = findViewById(R.id.etxtConfirmPassword);
        etxtPhone = findViewById(R.id.etxtPhone);
        checkTerms = findViewById(R.id.checkTerms);
        btnSubmit = findViewById(R.id.btnSubmit);
        mAuth = FirebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        validateInputs();
    }

    private void validateInputs() {
        validateNames(etxtName.getText().toString());
        validateNames(etxtLastName.getText().toString());
        validateEmail(etxtEmail.getText().toString());
        validatePassword(etxtPassword.getText().toString());
        confirmPassword(etxtPassword.getText().toString(), etxtConfirmPassword.getText().toString());
        validatePhone(etxtPhone.getText().toString());
        createFirebaseAuthDelivery();
    }

    private void validateNames(String name) {
        if (!name.matches(Patterns.NAME_PATTERN)) {
            etxtEmail.setError("Valor inválido");
            etxtEmail.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            etxtEmail.setError("Por favor ingresa un valor");
            etxtEmail.requestFocus();
            return;
        }
    }

    private void validateEmail(String email) {
        if (!email.contains("@javeriana.edu.co")) {
            etxtEmail.setError("Correo inválido");
            etxtEmail.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etxtEmail.setError("Por favor ingresa un correo");
            etxtEmail.requestFocus();
            return;
        }
    }

    private void validatePassword(String password) {
        if (password.isEmpty()) {
            etxtPassword.setError("Por favor ingresa una contraseña");
            etxtPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etxtPassword.setError("La contraseña debe tener más de 6 caracteres");
            etxtPassword.requestFocus();
            return;
        }

    }

    private void confirmPassword(String password, String confirmPassword) {
        if (!confirmPassword.equals(password)) {
            etxtConfirmPassword.setError("Las contraseñas deben ser iguales");
            etxtConfirmPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            etxtPassword.setError("Por favor ingresa una contraseña");
            etxtPassword.requestFocus();
            return;
        }
    }

    private void validatePhone(String phone) {
        if (phone.isEmpty()) {
            etxtPassword.setError("Por favor ingresa un telefono");
            etxtPassword.requestFocus();
            return;
        }
    }

    private DeliveryMan createDeliveryObject() {
        return new DeliveryMan(etxtName.getText().toString(),
                etxtLastName.getText().toString(),
                etxtEmail.getText().toString(),
                etxtPassword.getText().toString(),
                etxtPhone.getText().toString(),0d,0l);
    }

    private void createFirebaseAuthDelivery() {
        mAuth.createUserWithEmailAndPassword(etxtEmail.getText().toString(), etxtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    saveDelivery();
            }
        });
    }

    private void saveDelivery() {
        DeliveryMan delivery = createDeliveryObject();
        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(delivery).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    startActivity(new Intent(RegisterDeliveryScreenActivity.this, DeliveryHomeActivity.class));
                else
                    Toast.makeText(RegisterDeliveryScreenActivity.this, "Registro invalido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}