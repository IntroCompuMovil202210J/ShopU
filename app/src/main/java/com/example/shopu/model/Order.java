package com.example.shopu.model;

import com.example.shopu.enums.Payment;

import java.util.List;

public class Order {

    private String id;
    private String products;
    private String deliveryMan;
    private Payment payment;
    private Boolean completed;
    private Double longitude;
    private Double latitude;

    public Order() {
    }

    public Order(String products, String deliveryMan, Payment payment, Boolean completed, String id) {
        this.products = products;
        this.deliveryMan = deliveryMan;
        this.payment = payment;
        this.completed = completed;
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

