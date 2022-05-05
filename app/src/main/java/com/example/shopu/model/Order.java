package com.example.shopu.model;

import java.util.List;

public class Order {

    private String id;
    private List<Product> products;
    private DeliveryMan deliveryMan;
    private Client client;
    private Payment payment;
    private Boolean completed;
    private ChatMessage chat;

    public Order() {
    }

    public Order(List<Product> products, DeliveryMan deliveryMan, Client client, Payment payment, Boolean completed, ChatMessage chat, String id) {
        this.products = products;
        this.deliveryMan = deliveryMan;
        this.client = client;
        this.payment = payment;
        this.completed = completed;
        this.chat = chat;
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public ChatMessage getChat() {
        return chat;
    }

    public void setChat(ChatMessage chat) {
        this.chat = chat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

