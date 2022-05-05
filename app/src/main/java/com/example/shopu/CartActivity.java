package com.example.shopu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopu.adapters.ProductCartAdapter;
import com.example.shopu.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Button btnOrder;
    ListView lstCartProducts;
    ProductCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnOrder = findViewById(R.id.btnOrder);
        lstCartProducts = (ListView) findViewById(R.id.lstCartProducts);
        adapter = new ProductCartAdapter(this, loadProducts());

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order();
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
        Toast.makeText(CartActivity.this, "Orden hecha", Toast.LENGTH_SHORT).show();
    }
}