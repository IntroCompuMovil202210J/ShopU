package com.example.shopu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.shopu.R;
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

        Product product = this.products.get(position);

        txtName.setText(product.getName());
        txtDescrip.setText(product.getDescription());
        txtPrice.setText(product.getPrice().toString());

        return convertView;
    }



}
