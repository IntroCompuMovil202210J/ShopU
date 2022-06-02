package com.example.shopu.clientFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopu.FCMSend;
import com.example.shopu.HomeActivity;
import com.example.shopu.R;
import com.example.shopu.RegisterScreenActivity;
import com.example.shopu.adapters.ProductCartAdapter;
import com.example.shopu.model.Order;
import com.example.shopu.model.Product;
import com.example.shopu.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static ArrayList<Product> itemsToCart = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    FirebaseAuth mAuth;
    FirebaseDatabase db;
    Button btnOrder;
    ListView lstCartProducts;
    ProductCartAdapter adapter;

    View root;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_cart, container, false);

        btnOrder = root.findViewById(R.id.btnOrder);
        lstCartProducts = (ListView) root.findViewById(R.id.lstCartProducts);
        adapter = new ProductCartAdapter(getContext(), itemsToCart);
        lstCartProducts.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId = String.valueOf(Math.floor(Math.random()*(100000-1000+1)+1000));
                Order order = createOrder(orderId.substring(0,orderId.length()-2));
                FirebaseDatabase.getInstance().getReference("orders")
                        .child(orderId.substring(0,orderId.length()-2))
                        .setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("Done");
                                } else {
                                    Toast.makeText(getContext(), "Registro invalido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                FCMSend.pushNotification(getContext(),
                        "cpG4sUm7SD-ghWpStppY50:APA91bGwJQQ5t9V09v_lGMsVkUuRUULWC07mSDb1tMkNlOHAB0v8WQLADS8AQW68aMx7eKM1j5LGDl63KLK22WG699LLVxrPU7mp6aocz_Ggz-xB6KD5SBsBNZCjM_hiSFOPNmkk6-b1",
                        "Orden Creada",
                        "Se ha creado una nueva orden");
                System.out.println("ORDENANDO ...");
            }
        });

        return root;
    }

    public Order createOrder(String id){
        Order order = new Order();
        String products = "";

        for (Product p: CartFragment.itemsToCart) {
            products =  products +"-"+p.getName();
        }

        order.setCompleted(false);
        order.setId(mAuth.getCurrentUser().getUid());
        order.setProducts(products);
        order.setDeliveryMan("Not Set");
        order.setId(id);
        order.setLatitude(4.736593);
        order.setLongitude(-74.087583);

        return order;
    }


}