package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeliveryHomeActivity extends AppCompatActivity {

    Button btnProfile;
    Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_home);

        btnProfile = findViewById(R.id.btnProfile);
        btnOrder = findViewById(R.id.btnOrders);



        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),OrdersActivity.class));
            }
        });


    }
}