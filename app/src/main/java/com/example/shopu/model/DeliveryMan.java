package com.example.shopu.model;

import com.example.shopu.enums.UserType;

public class DeliveryMan extends User{

    private Double Score;
    private Long profit;

    public DeliveryMan() {
        super();
    }

    public DeliveryMan(String token, String name, String lastName, String email, String password, String phone, Double latitude, Double longitude) {
        super(token, name, lastName, email, password, phone, latitude, longitude);
    }

    public Double getScore() {
        return Score;
    }

    public void setScore(Double score) {
        Score = score;
    }

    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;
    }

    public void setLatitude(Double latitude) {
        super.setLatitude(latitude);
    }

    public void setLongitude(Double longitude) {
        super.setLongitude(longitude);
    }

    public void setToken(String token) {
        super.setToken(token);
    }

    @Override
    public String getType(){
        return UserType.DELIVERY_MAN.toString();
    }

    @Override
    public String toString() {
        return getLatitude() + " - " + getLongitude();
    }
}
