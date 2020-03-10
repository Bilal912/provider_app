package com.example.bus_reservation.Model;

public class bookings_model {
    String bookingid;
    String client_id;
    String client_name;
    String service_name;
    String service_type;
    String start_date;
    String start_time;
    String hours;
    String hourly_price;
    String title_price;
    String location_type;
    String hotel_room;
    String address;
    String special_request;
    String id_image;
    String accepted;
    String pay_status;


    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public bookings_model() {
    }

    public bookings_model(String bookingid, String client_id, String client_name, String service_name, String service_type, String start_date, String start_time, String hours, String hourly_price, String title_price, String location_type, String hotel_room, String address, String special_request, String id_image, String accepted, String pay_status) {
        this.bookingid = bookingid;
        this.client_id = client_id;
        this.client_name = client_name;
        this.service_name = service_name;
        this.service_type = service_type;
        this.start_date = start_date;
        this.start_time = start_time;
        this.hours = hours;
        this.hourly_price = hourly_price;
        this.title_price = title_price;
        this.location_type = location_type;
        this.hotel_room = hotel_room;
        this.address = address;
        this.special_request = special_request;
        this.id_image = id_image;
        this.accepted = accepted;
        this.pay_status = pay_status;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(String hourly_price) {
        this.hourly_price = hourly_price;
    }

    public String getTitle_price() {
        return title_price;
    }

    public void setTitle_price(String title_price) {
        this.title_price = title_price;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public String getHotel_room() {
        return hotel_room;
    }

    public void setHotel_room(String hotel_room) {
        this.hotel_room = hotel_room;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecial_request() {
        return special_request;
    }

    public void setSpecial_request(String special_request) {
        this.special_request = special_request;
    }

    public String getId_image() {
        return id_image;
    }

    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }
}
