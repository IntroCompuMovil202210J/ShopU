package com.example.shopu.model;

import com.example.shopu.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class Client extends User{

    private Cart cart;
    private List<Card> Cards;


    public Client(){
        super();
    }

    public Client(String Token, String name, String lastName, String email, String password, String phone, Double latitude, Double longitude) {
        super(Token, name, lastName, email, password, phone, latitude, longitude);
        this.cart = cart = new Cart();
        Cards = new ArrayList<>();
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
        return UserType.CLIENT.toString();
    }

}
