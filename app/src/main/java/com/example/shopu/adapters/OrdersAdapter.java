package com.example.shopu.adapters;

        import android.content.Context;
        import android.database.Cursor;
        import android.location.Geocoder;
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

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {

    List<Order> orders;
    Geocoder mGeocoder= new Geocoder(getContext());

    private final double RADIUS_OF_EARTH_KM = 6371.01;

    public OrdersAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);

        this.orders = orders;
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
        TextView txtItems = convertView.findViewById(R.id.txtItems);
        Button boton = (Button) convertView.findViewById(R.id.selectOrder);

        Order order = this.orders.get(position);
        // Populate the data into the template view using the data object
        txtOrden.setText("orden");

//        try {
//            txtDirec.setText(mGeocoder.getFromLocation(order.getLatitude(),order.getLongitude(),1).get(0).getAddressLine(0));
            txtDirec.setText(order.getLatitude() + "-"+ order.getLongitude());
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//       }
        txtItems.setText(order.getProducts());

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }





}
