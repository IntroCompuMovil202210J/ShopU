package com.example.shopu.adapters;

        import android.content.Context;
        import android.database.Cursor;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CursorAdapter;
        import android.widget.TextView;
        import com.example.shopu.R;
        import com.example.shopu.model.Establishment;
        import com.example.shopu.model.Order;

        import java.util.ArrayList;
        import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {

    List<Order> orders;
    private final double RADIUS_OF_EARTH_KM = 6371.01;

    public OrdersAdapter(Context context, ArrayList<Order> orders) {
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
        Button boton = (Button) convertView.findViewById(R.id.selectOrder);

        Order order = this.orders.get(position);
        // Populate the data into the template view using the data object
        Double distancia = distance(order.getClient().getLocation().getLatitude(),order.getClient().getLocation().getLongitude(),order.getDeliveryMan().getLocation().getLatitude()
        ,order.getDeliveryMan().getLocation().getLongitude());
        txtOrden.setText(distancia.toString());

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        // Return the completed view to render on screen
        return convertView;
    }

    public double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }


}
