package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.adapters.ProductAdapter;
import com.example.shopu.model.Product;

import java.util.ArrayList;
import java.util.List;

public class EstablecimientoActivity extends AppCompatActivity {

    ListView lvwEstProduct;
    ArrayList<Product> products;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento);

        lvwEstProduct = findViewById(R.id.lvwEstProducts);
        loadProducts();

        productAdapter = new ProductAdapter(getApplicationContext(),products);
        lvwEstProduct.setAdapter(productAdapter);

    }

    public void loadProducts(){

        this.products = new ArrayList<>();

        Product p1 = new Product(); p1.setName("Hamburguesa"); p1.setDescription("Una hamburguesa"); p1.setPrice(20000);
        Product p2 = new Product(); p1.setName("Lapiz"); p1.setDescription("Un Lapiz H2"); p1.setPrice(20000);
        Product p3 = new Product(); p1.setName("Borrador"); p1.setDescription("Un Borrador"); p1.setPrice(20000);
        Product p4 = new Product(); p1.setName("Papas Fritas"); p1.setDescription("Unas papitas ricas"); p1.setPrice(20000);
        Product p5 = new Product(); p1.setName("Sevedol"); p1.setDescription("Something..."); p1.setPrice(20000);
        Product p6 = new Product(); p1.setName("Naproxeno"); p1.setDescription("Una Naproxeno"); p1.setPrice(20000);
        Product p7 = new Product(); p1.setName("Dolex"); p1.setDescription("Un dolex"); p1.setPrice(20000);

    }

}