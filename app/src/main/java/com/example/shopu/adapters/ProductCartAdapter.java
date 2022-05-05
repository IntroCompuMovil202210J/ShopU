package com.example.shopu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopu.R;
import com.example.shopu.model.Establishment;
import com.example.shopu.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductCartAdapter extends ArrayAdapter<Product> {
    ArrayList<Product> products;

    public ProductCartAdapter(@NonNull Context context, ArrayList<Product> products) {
        super(context, 0, products);
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_restaurant, parent, false);
        // Lookup view for data population
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);

        Product product = this.products.get(position);
        // Populate the data into the template view using the data object
        txtName.setText(product.getName());
        txtPrice.setText(product.getPrice().toString());

        // Return the completed view to render on screen
        return convertView;
    }
}
