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
import com.example.shopu.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
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

    public void order() {
        if (products != null)
            Toast.makeText(CartActivity.this, "Orden hecha", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(CartActivity.this, "Por favor agrega algún producto", Toast.LENGTH_SHORT).show();
    }
}