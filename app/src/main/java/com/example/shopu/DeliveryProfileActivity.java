package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopu.model.DeliveryMan;
import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeliveryProfileActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    Button logOut;
    EditText Dname, Dlastname,  Dphone;
    TextView score, Demail, profit;
    DatabaseReference ref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageView btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_profile);

        Dname = findViewById(R.id.Dname);
        Dlastname = findViewById(R.id.Dlastname);
        Demail = findViewById(R.id.Demail);
        Dphone = findViewById(R.id.Dphone);
        logOut = findViewById(R.id.logOut);
        score = findViewById(R.id.score);
        profit = findViewById(R.id.profit);
        btnHome = findViewById(R.id.btn_home);

        mAuth = FirebaseAuth.getInstance();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeliveryProfileActivity.this,DeliveryHomeActivity.class));
            }
        });


        ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        DeliveryMan usuario = task.getResult().getValue(DeliveryMan.class);
                        Dname.setText(usuario.getName());
                        Dlastname.setText(usuario.getLastName());
                        Demail.setText(usuario.getEmail());
                        Dphone.setText(usuario.getPhone());
                        score.setText(usuario.getScore().toString());
                        profit.setText(usuario.getProfit().toString());


                    }
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                startActivity(new Intent(DeliveryProfileActivity.this,MainActivity.class));

            }
        });
    }
}
