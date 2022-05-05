package com.example.shopu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopu.adapters.OrdersAdapter;
import com.example.shopu.adapters.ProductCartAdapter;
import com.example.shopu.model.Location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    ListView lstOrders;
    OrdersAdapter adapter;
    private ArrayList<Location> locations;
    private double latitudeeee;
    private final double RADIUS_OF_EARTH_KM = 6371.01;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("orders");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        loadLocations();

        locations = new ArrayList<>();
        lstOrders = (ListView) findViewById(R.id.ordersList);
        adapter = new OrdersAdapter(this,locations);
        lstOrders.setAdapter(adapter);




    }


    public void loadLocations() {


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Location location = singleSnapshot.getValue(Location.class);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ERROR", "error en la consulta", databaseError.toException());
            }
        });







    }
    public double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result * 100.0) / 100.0;
    }
}