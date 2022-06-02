package com.example.shopu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopu.R;
import com.example.shopu.clientFragments.CartFragment;
import com.example.shopu.clientFragments.EstablishmentFragment;
import com.example.shopu.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    List<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        this.products= products;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.txtNameP);
        TextView txtDescrip =(TextView) convertView.findViewById(R.id.txtDescriptionP);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPriceP);
        Button add = convertView.findViewById(R.id.btnAdd);
        Product product = this.products.get(position);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment.itemsToCart.add(product);
                Toast.makeText(getContext(),"Se agrego un(a) "+product.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        txtName.setText(product.getName());
        txtDescrip.setText(product.getDescription());
        txtPrice.setText(product.getPrice().toString());

        return convertView;
    }



}
