package com.example.shopu.clientFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopu.R;
import com.example.shopu.adapters.ProductAdapter;
import com.example.shopu.enums.EstablishmentCategory;
import com.example.shopu.model.Establishment;
import com.example.shopu.model.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstablishmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstablishmentFragment extends Fragment {



    private Establishment establishment;
    ListView lvwEstProduct;
    TextView txtName,txtScore;


    ArrayList<Product> products;
    private ProductAdapter productAdapter;

    TextView txt;
    View root;

    public EstablishmentFragment() {
    }

    public static EstablishmentFragment newInstance(String param1, String param2) {
        EstablishmentFragment fragment = new EstablishmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           this.establishment = (Establishment)getArguments().getSerializable("establishment");
           Bundle bundle = new Bundle();
           bundle.putString("data","Datos enviados");
           this.setArguments(bundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_establishment, container, false);

        lvwEstProduct = root.findViewById(R.id.lvwEstProducts);
        txtName = root.findViewById(R.id.txtName);
        txtScore = root.findViewById(R.id.scoreEstablishment);
        loadProducts();

        productAdapter = new ProductAdapter(getContext(),products);
        lvwEstProduct.setAdapter(productAdapter);


        return root;
    }

    public void loadProducts(){

        txtName.setText(establishment.getName());

        txtScore.setText(establishment.getScore());

        this.products = new ArrayList<>();
        if(establishment.getCategory() == EstablishmentCategory.FEEDING){

            Product p1 = new Product(); p1.setName("Hamburguesa"); p1.setDescription("Una hamburguesa"); p1.setPrice(20000);
            Product p2 = new Product(); p2.setName("Gaseosa"); p2.setDescription("Una gaseosa"); p2.setPrice(20000);
            Product p3 = new Product(); p3.setName("Papas"); p3.setDescription("Unas papas"); p3.setPrice(20000);
            Product p4 = new Product(); p4.setName("Papas Fritas"); p4.setDescription("Unas papitas ricas"); p4.setPrice(20000);
            Product p5 = new Product(); p5.setName("Perro Caliente"); p5.setDescription("Something..."); p5.setPrice(20000);
            Product p6 = new Product(); p6.setName("Super perro caliente"); p6.setDescription("Una perrote"); p6.setPrice(20000);
            Product p7 = new Product(); p7.setName("Perrito"); p7.setDescription("Un Perrito"); p7.setPrice(20000);

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);
            products.add(p6);
            products.add(p7);

        }

        if(establishment.getCategory() == EstablishmentCategory.PHARMACY){

            Product p1 = new Product(); p1.setName("Dolex"); p1.setDescription("Antigripal..."); p1.setPrice(20000);
            Product p2 = new Product(); p2.setName("DolexForte"); p2.setDescription("Antigripal..."); p2.setPrice(20000);
            Product p3 = new Product(); p3.setName("Dolex Ultra"); p3.setDescription("Antigripal..."); p3.setPrice(20000);
            Product p4 = new Product(); p4.setName("DolexUltraForte"); p4.setDescription("Antigripal..."); p4.setPrice(20000);
            Product p5 = new Product(); p5.setName("DolexNotSoForte"); p5.setDescription("Antigripal..."); p5.setPrice(20000);
            Product p6 = new Product(); p6.setName("CommonDolex"); p6.setDescription("Antigripal..."); p6.setPrice(20000);
            Product p7 = new Product(); p7.setName("Advil"); p7.setDescription("Antigripal..."); p7.setPrice(20000);

            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            products.add(p5);
            products.add(p6);
            products.add(p7);

        }

    }
}