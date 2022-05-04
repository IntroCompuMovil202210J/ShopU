package com.example.shopu.model;

public class Product {

    private String name;
    private String description;
    private Integer Price;

    public Product() {
    }

    public Product(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        Price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }
}