package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopu.enums.UserType;
import com.example.shopu.model.Client;
import com.example.shopu.model.DeliveryMan;
import com.example.shopu.model.User;
import com.example.shopu.utils.Patterns;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterDeliveryScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText etxtName, etxtLastName, etxtEmail, etxtPassword, etxtConfirmPassword, etxtPhone;
    CheckBox checkTerms;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_delivery_screen);

        etxtName = findViewById(R.id.etxtName);
        etxtLastName = findViewById(R.id.etxtLastName);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtConfirmPassword = findViewById(R.id.etxtConfirmPassword);
        etxtPhone = findViewById(R.id.etxtPhone);
        checkTerms = findViewById(R.id.checkTerms);
        btnSubmit = findViewById(R.id.btnSubmit);
        mAuth = FirebaseAuth.getInstance();
    }

    public void submit(View view) {
        boolean validName = validateName(etxtName.getText().toString());
        boolean validLastName = validateLastName(etxtLastName.getText().toString());
        boolean validEmail = validateEmail(etxtEmail.getText().toString());
        boolean validPassword = validatePassword(etxtPassword.getText().toString());
        boolean validPasswordConfirmation = confirmPassword(etxtPassword.getText().toString(), etxtConfirmPassword.getText().toString());
        boolean validPhone = validatePhone(etxtPhone.getText().toString());
        if (validName && validLastName && validEmail && validPassword && validPasswordConfirmation && validPhone)
            validateIfUsersAlreadyExists();
    }

    private boolean validateName(String name) {
        boolean flag = true;
        if (name.isEmpty()) {
            etxtName.setError("Por favor ingresa un valor");
            etxtName.requestFocus();
            flag = false;
        }
        if (!name.matches(Patterns.NAME_PATTERN)) {
            etxtName.setError("Valor inválido");
            etxtName.requestFocus();
            flag = false;
        }
        return flag;
    }

    private boolean validateLastName(String lastName) {
        boolean flag = true;
        if (lastName.isEmpty()) {
            etxtEmail.setError("Por favor ingresa un valor");
            etxtEmail.requestFocus();
            flag = false;
        }
        if (!lastName.matches(Patterns.NAME_PATTERN)) {
            etxtLastName.setError("Valor inválido");
            etxtLastName.requestFocus();
            flag = false;
        }
        return flag;
    }

    private boolean validateEmail(String email) {
        boolean flag = true;
        if (email.isEmpty()) {
            etxtEmail.setError("Por favor ingresa un correo");
            etxtEmail.requestFocus();
            flag = false;
        }
        if (!email.contains("@javeriana.edu.co")) {
            etxtEmail.setError("Correo inválido");
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
        if (password.length() < 6) {
            etxtPassword.setError("La contraseña debe tener más de 6 caracteres");
            etxtPassword.requestFocus();
            flag = false;
        }
        return flag;
    }

    private boolean confirmPassword(String password, String confirmPassword) {
        boolean flag = true;
        if (confirmPassword.isEmpty()) {
            etxtConfirmPassword.setError("Por favor ingresa una contraseña");
            etxtConfirmPassword.requestFocus();
            flag = false;
        }
        if (!confirmPassword.equals(password)) {
            etxtConfirmPassword.setError("Las contraseñas deben ser iguales");
            etxtConfirmPassword.requestFocus();
            flag = false;
        }
        return flag;
    }

    private boolean validatePhone(String phone) {
        boolean flag = true;
        if (phone.isEmpty()) {
            etxtPhone.setError("Por favor ingresa un telefono");
            etxtPhone.requestFocus();
            flag = false;
        }
        return flag;
    }

    private User createUserObject() {
        DeliveryMan user = new DeliveryMan(
                "",
                etxtName.getText().toString(),
                etxtLastName.getText().toString(),
                etxtEmail.getText().toString(),
                etxtPassword.getText().toString(),
                etxtPhone.getText().toString(),
                0d,
                0d);

        user.setProfit(0l);
        user.setScore(3d);
        user.setType(UserType.DELIVERY_MAN.toString());
        return user;
    }

    private void createFirebaseAuthUser() {
        mAuth.createUserWithEmailAndPassword(etxtEmail.getText().toString(), etxtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) saveUser();
            }
        });
    }

    private void saveUser() {
        User deliveryMan = createUserObject();
        FirebaseDatabase.getInstance().getReference("users")
                .child(mAuth.getCurrentUser().getUid())
                .setValue(deliveryMan).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("📁 USER SAVED", deliveryMan.toString());
                            startActivity(new Intent(RegisterDeliveryScreenActivity.this, DeliveryHomeActivity.class));
                        }
                        else {
                            Log.i("📁 USER NOT SAVED", deliveryMan.toString());
                            Toast.makeText(RegisterDeliveryScreenActivity.this, "Registro invalido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void validateIfUsersAlreadyExists() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                boolean flag = false;
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if (user.getEmail().equals(etxtEmail.getText().toString()) || user.getPhone().equals(etxtPhone.getText().toString()))
                                flag = true;
                        }
                        if (flag)
                            Toast.makeText(RegisterDeliveryScreenActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                        else createFirebaseAuthUser();
                    }
                } else createFirebaseAuthUser();
            }
        });
    }
}