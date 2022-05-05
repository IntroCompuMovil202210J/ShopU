package com.example.shopu;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnSignin, btnDelivery;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference myRef;
    public static final String PATH_USERS="users/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignin = findViewById(R.id.btnSignin);
        btnDelivery = findViewById(R.id.btnCreateDelivery);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (user!=null){
            myRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()){

                        if(task.getResult().exists()){
                            User usuario = task.getResult().getValue(User.class);

                            if(usuario.getType().equals("deliveryMan"))
                                startActivity(new Intent(MainActivity.this,DeliveryHomeActivity.class));
                            else
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        }
                    }
                }
            });

        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(view.getContext(),LogScreenActivity.class);
                startActivity(log);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(view.getContext(),RegisterScreenActivity.class);
                startActivity(reg);
            }
        });

        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regDelv = new Intent(view.getContext(),RegisterDeliveryScreenActivity.class);
                startActivity(regDelv);
            }
        });
    }
}