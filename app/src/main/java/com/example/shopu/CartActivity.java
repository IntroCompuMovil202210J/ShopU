package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.adapters.ProductCartAdapter;
//import com.example.shopu.model.Location;
import com.example.shopu.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    Button btnOrder;
    ListView lstCartProducts;
    ProductCartAdapter adapter;
    BottomNavigationView navbar;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        products = loadProducts();
        btnOrder = findViewById(R.id.btnOrder);
        lstCartProducts = (ListView) findViewById(R.id.lstCartProducts);
        adapter = new ProductCartAdapter(this, products);
        navbar = findViewById(R.id.navbar);
        lstCartProducts.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
            }
        });

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemClicked = item.getItemId();

                switch (itemClicked){

                    case R.id.home:
                        startActivity(new Intent(CartActivity.this, HomeActivity.class));
                    case R.id.user:
                        startActivity(new Intent(CartActivity.this, UserProfileActivity.class));
                }
                return false;
            }
        });
    }

    private ArrayList<Product> loadProducts() {
        ArrayList<Product> returnList = new ArrayList<>();
        returnList.add(new Product("Hamburguesa", null, 20000));
        returnList.add(new Product("Hamburguesa", null, 20000));
        returnList.add(new Product("Hamburguesa", null, 20000));
        returnList.add(new Product("Hamburguesa", null, 20000));
        return returnList;
    }

    private void order() {

        if (products != null){
            Double latitude = getIntent().getDoubleExtra("latitude", 0d);
            Double longitude = getIntent().getDoubleExtra("longitude", 0d);
            Intent i = new Intent(getApplicationContext(),SeguimientoActivity.class);
            i.putExtra("latitude", latitude);
            i.putExtra("longitude", longitude);
            startActivity(i);
        }

        else
            Toast.makeText(CartActivity.this, "Por favor agrega algún producto", Toast.LENGTH_SHORT).show();
    }

    private void publishOrder() {

        // db.getReference("orders").push().setValue(new Location(latitude, longitude));
    }

}