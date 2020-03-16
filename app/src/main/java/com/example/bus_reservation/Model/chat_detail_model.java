package com.example.bus_reservation.Model;

public class chat_detail_model {
    String id;
    String name;
    String image;
    String client_id;

    public chat_detail_model() {
    }

    public chat_detail_model(String id, String name, String image, String client_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.client_id = client_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
