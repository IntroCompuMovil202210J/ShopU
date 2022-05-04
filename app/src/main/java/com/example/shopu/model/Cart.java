package com.example.shopu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private Map<Product,Integer> products;
    private Long total;

    public Cart() {
        this.products = new HashMap<>();
        this.total = 0l;
    }

    public Cart(Map<Product,Integer> products, Long total) {
        this.products = products;
        this.total = total;
    }

    public Map<Product,Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product,Integer> products) {
        this.products = products;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void addProduct(Product productToAdd,Integer number){
        this.products.put(productToAdd,number);
        this.total = calculateTotal();
    }

    public Long calculateTotal(){
        Long total = 0l;
        for (Map.Entry<Product,Integer> entry : products.entrySet()){
            total = total + entry.getValue();
        }
        return total;
    }
}
