package com.example.shopu.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.shopu.EstablecimientoActivity;
import com.example.shopu.HomeActivity;
import com.example.shopu.LogScreenActivity;
import com.example.shopu.R;
import com.example.shopu.clientFragments.EstablishmentFragment;
import com.example.shopu.model.Establishment;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentAdapter extends ArrayAdapter<Establishment> {

    List<Establishment> establishments;
    Fragment fragment;
    public EstablishmentAdapter(Context context, ArrayList<Establishment> establishments , Fragment fragment) {
        super(context, 0, establishments);

        this.establishments = establishments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_establishment, parent, false);
        }
        // Lookup view for data population
        TextView txtName = (TextView) convertView.findViewById(R.id.txtEstTime);
        ImageButton imgEstablishment = convertView.findViewById(R.id.imgEstablishment);

        Establishment establishment = this.establishments.get(position);
        // Populate the data into the template view using the data object
        txtName.setText(establishment.getName());

        imgEstablishment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
                fragment = new EstablishmentFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("establishment",establishment);
                fragment.setArguments(bundle);
                transaction.replace(R.id.fragment_layout, fragment);
                transaction.commit();
            }
        });

        return convertView;
    }


}
