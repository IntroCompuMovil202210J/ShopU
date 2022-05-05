package com.example.shopu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Establishment implements Serializable {

    private String name;
    private String address;
    private String Score;
    private String Photo;
    private List<Product> productList;
    private EstablishmentCategory category;



    public Establishment() {
        this.productList = new ArrayList<Product>();
    }

    public Establishment(String name, String address, String score, String photo, List<Product> productList,EstablishmentCategory category) {
        this.name = name;
        this.address = address;
        Score = score;
        Photo = photo;
        this.productList = productList;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public EstablishmentCategory getCategory() {
        return category;
    }

    public void setCategory(EstablishmentCategory category) {
        this.category = category;
    }
}
