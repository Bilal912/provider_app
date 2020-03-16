package com.example.bus_reservation.Model;

public class chat_model {
    String id;
    String chat_id;
    String sender_id;
    String msg;
    String image_flag;
    String date;

    public chat_model() {
    }

    public chat_model(String id, String chat_id, String sender_id, String msg, String image_flag, String date) {
        this.id = id;
        this.chat_id = chat_id;
        this.sender_id = sender_id;
        this.msg = msg;
        this.image_flag = image_flag;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getImage_flag() {
        return image_flag;
    }

    public void setImage_flag(String image_flag) {
        this.image_flag = image_flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
