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
        import com.example.shopu.model.Order;

        import java.util.ArrayList;
        import java.util.List;

public class OrdersAdapter extends ArrayAdapter<Order> {

    List<Order> orders;

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
        TextView txtName = (TextView) convertView.findViewById(R.id.txtEstTime);

        Order order = this.orders.get(position);
        // Populate the data into the template view using the data object
        //txtName.setText(order);

        // Return the completed view to render on screen
        return convertView;
    }

}
