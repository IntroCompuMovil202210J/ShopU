package com.example.shopu.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.shopu.R;
import com.example.shopu.model.Establishment;

import java.util.ArrayList;

public class EstablishMentAdapter extends ArrayAdapter<Establishment> {

    public EstablishMentAdapter(Context context, ArrayList<Establishment> establishments) {
        super(context, 0, establishments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Establishment establishment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_establishment, parent, false);
        }
        // Lookup view for data population
        TextView txtName = (TextView) convertView.findViewById(R.id.txtEstTime);
        // Populate the data into the template view using the data object
        txtName.setText(establishment.getName());

        // Return the completed view to render on screen
        return convertView;
    }

}
