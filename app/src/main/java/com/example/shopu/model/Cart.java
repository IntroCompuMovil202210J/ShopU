package com.example.shopu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private ArrayList<Product> products;
    private Long total;

    public Cart() {
        this.products = new ArrayList<>();
        this.total = 0l;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        this.total = calculateTotal();
    }

    public Long calculateTotal() {
        Long total = 0l;
        for (Product p : products)
            total += p.getPrice();
        return total;
    }
}
