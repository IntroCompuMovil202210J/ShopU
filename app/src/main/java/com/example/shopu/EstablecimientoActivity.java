package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopu.adapters.EstablishmentAdapter;
import com.example.shopu.adapters.ProductAdapter;
import com.example.shopu.model.Establishment;
import com.example.shopu.model.EstablishmentCategory;
import com.example.shopu.model.Product;

import java.util.ArrayList;
import java.util.List;

public class EstablecimientoActivity extends AppCompatActivity {

    ListView lvwEstProduct;
    TextView txtName;
    Establishment establishment;

    ArrayList<Product> products;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento);

        establishment = (Establishment) getIntent().getSerializableExtra("EstabSended");

        lvwEstProduct = findViewById(R.id.lvwEstProducts);
        txtName = findViewById(R.id.txtName);
        loadProducts();

        productAdapter = new ProductAdapter(getApplicationContext(),products);
        lvwEstProduct.setAdapter(productAdapter);


    }

    public void loadProducts(){

        txtName.setText(establishment.getName());

        this.products = new ArrayList<>();

        if(establishment.getCategory() == EstablishmentCategory.FEEDING){

            Product p1 = new Product(); p1.setName("Hamburguesa"); p1.setDescription("Una hamburguesa"); p1.setPrice(20000);
            Product p2 = new Product(); p2.setName("Gaseosa"); p2.setDescription("Una gaseosa"); p2.setPrice(20000);
            Product p3 = new Product(); p3.setName("Papas"); p3.setDescription("Unas papas"); p3.setPrice(20000);
            Product p4 = new Product(); p4.setName("Papas Fritas"); p4.setDescription("Unas papitas ricas"); p4.setPrice(20000);
            Product p5 = new Product(); p5.setName("Perro Caliente"); p5.setDescription("Something..."); p5.setPrice(20000);
            Product p6 = new Product(); p6.setName("Super perro caliente"); p6.setDescription("Una perrote"); p6.setPrice(20000);
            Product p7 = new Product(); p7.setName("Perrito"); p7.setDescription("Un Perrito"); p7.setPrice(20000);

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);
            products.add(p6);
            products.add(p7);


        }

        if(establishment.getCategory() == EstablishmentCategory.PHARMACY){

            Product p1 = new Product(); p1.setName("Dolex"); p1.setDescription("Antigripal..."); p1.setPrice(20000);
            Product p2 = new Product(); p2.setName("DolexForte"); p2.setDescription("Antigripal..."); p2.setPrice(20000);
            Product p3 = new Product(); p3.setName("Dolex Ultra"); p3.setDescription("Antigripal..."); p3.setPrice(20000);
            Product p4 = new Product(); p4.setName("DolexUltraForte"); p4.setDescription("Antigripal..."); p4.setPrice(20000);
            Product p5 = new Product(); p5.setName("DolexNotSoForte"); p5.setDescription("Antigripal..."); p5.setPrice(20000);
            Product p6 = new Product(); p6.setName("CommonDolex"); p6.setDescription("Antigripal..."); p6.setPrice(20000);
            Product p7 = new Product(); p7.setName("Advil"); p7.setDescription("Antigripal..."); p7.setPrice(20000);

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);
            products.add(p6);
            products.add(p7);

        }


    }



}