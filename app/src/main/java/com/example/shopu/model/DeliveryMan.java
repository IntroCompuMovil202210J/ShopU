package com.example.shopu.model;

import com.example.shopu.enums.UserType;

public class DeliveryMan extends User{

    private Double Score;
    private Long profit;

    public DeliveryMan() {
        super();
    }

    public DeliveryMan(String name, String lastName, String email, String password, String phone) {
        super(name, lastName, email, password, phone);
        this.Score = 0d;
        this.profit = 0L;
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

    @Override
    public String getType(){
        return UserType.DELIVERY_MAN.toString();
    }

}
