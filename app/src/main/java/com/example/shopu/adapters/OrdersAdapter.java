package com.example.shopu.adapters;

        import android.content.Context;
        import android.database.Cursor;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CursorAdapter;
        import android.widget.TextView;
        import com.example.shopu.R;
        import com.example.shopu.model.Establishment;
        import com.example.shopu.model.Location;
        import com.example.shopu.model.Order;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Location> {

    List<Order> orders;
    List<Location> locations;

    private final double RADIUS_OF_EARTH_KM = 6371.01;

    public OrdersAdapter(Context context, List<Location> orders) {
        super(context, 0, orders);

        this.locations = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_orders, parent, false);
        }
        // Lookup view for data population
        TextView txtOrden = (TextView) convertView.findViewById(R.id.txtOrder);
        TextView txtDirec = (TextView) convertView.findViewById(R.id.txtDirection);
        Button boton = (Button) convertView.findViewById(R.id.selectOrder);

        Location order = this.locations.get(position);
        // Populate the data into the template view using the data object
        txtOrden.setText("orden");
        txtDirec.setText(order.getLatitude().toString() +" - " + order.getLongitude().toString());


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // Return the completed view to render on screen
        return convertView;
    }





}
