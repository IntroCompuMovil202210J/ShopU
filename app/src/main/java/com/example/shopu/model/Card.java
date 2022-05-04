package com.example.shopu.model;

import java.util.Date;

public class Card {

    private Long number;
    private Date date;
    private Integer CCV;

    public Card() {
    }

    public Card(Long number, Date date, Integer CCV) {
        this.number = number;
        this.date = date;
        this.CCV = CCV;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCCV() {
        return CCV;
    }

    public void setCCV(Integer CCV) {
        this.CCV = CCV;
    }
}