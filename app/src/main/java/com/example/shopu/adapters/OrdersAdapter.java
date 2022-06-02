package com.example.shopu.adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.location.Geocoder;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.annotation.NonNull;

        import com.example.shopu.DeliveryTrackOrderActivity;
        import com.example.shopu.R;
        import com.example.shopu.model.Order;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {

    Context mContext;
    List<Order> orders;
    Geocoder mGeocoder= new Geocoder(getContext());

    private final double RADIUS_OF_EARTH_KM = 6371.01;

    public OrdersAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
        this.mContext = context;
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
        updateUi(boton, order.getId());

        return convertView;
    }

    private void updateUi(Button button, String orderId) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child(orderId).child("deliveryMan");
                ref.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Intent intent = new Intent(mContext, DeliveryTrackOrderActivity.class);
                intent.putExtra("order", orderId);
                mContext.startActivity(intent);
            }
        });
    }



}
