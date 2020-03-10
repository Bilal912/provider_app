package com.example.bus_reservation.Model;

public class gallery_model {
    String id;
    String image;
    String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public gallery_model() {
    }

    public gallery_model(String id, String image, String date) {
        this.id = id;
        this.image = image;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
