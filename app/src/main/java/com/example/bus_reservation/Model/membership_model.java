package com.example.bus_reservation.Model;

public class membership_model {

    String membership_id;
    String name;
    String price;
    String see;
    String buy;

    public membership_model() {
    }

    public membership_model(String membership_id, String name, String price, String see, String buy) {
        this.membership_id = membership_id;
        this.name = name;
        this.price = price;
        this.see = see;
        this.buy = buy;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSee() {
        return see;
    }

    public void setSee(String see) {
        this.see = see;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }
}
