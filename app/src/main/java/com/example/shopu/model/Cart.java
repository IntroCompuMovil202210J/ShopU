package com.example.shopu.model;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<String,Integer> products;
    private Long total;

    public Cart() {
        this.products = new HashMap<>();
        this.total = 0l;
    }

    public Cart(Map<String,Integer> products, Long total) {
        this.products = products;
        this.total = total;
    }

    public Map<String,Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String,Integer> products) {
        this.products = products;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void addProduct(Product productToAdd,Integer number){
        this.products.put(productToAdd.getName(),number);
        this.total = calculateTotal();
    }

    public Long calculateTotal(){
        Long total = 0l;
        for (Map.Entry<String,Integer> entry : products.entrySet()){
            total = total + entry.getValue();
        }
        return total;
    }
}
