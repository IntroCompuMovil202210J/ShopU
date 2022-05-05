package com.example.shopu.model;

import java.util.List;

public class Client extends User{

    private Cart cart;
    private List<Card> Cards;


    public Client(){
        super();
    }

    public Client(String name, String lastName, String email, String password, String phone, Cart cart, List<Card> cards, Location location) {
        super(name, lastName, email, password, phone);
        this.cart = cart;
        Cards = cards;
        super.setLocation(location);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Card> getCards() {
        return Cards;
    }

    public void setCards(List<Card> cards) {
        Cards = cards;
    }



    @Override
    public String getType(){
        return "client";
    }

}
