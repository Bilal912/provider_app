package com.example.bus_reservation.Model;

public class service_model {
String sevice_id;
String title;
String service_name;
String incall_rate;
String outcall_rate;
String description;
String cat_name;

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public service_model() {
    }

    public service_model(String sevice_id, String title, String service_name, String incall_rate, String outcall_rate, String description) {
        this.sevice_id = sevice_id;
        this.title = title;
        this.service_name = service_name;
        this.incall_rate = incall_rate;
        this.outcall_rate = outcall_rate;
        this.description = description;
    }

    public String getSevice_id() {
        return sevice_id;
    }

    public void setSevice_id(String sevice_id) {
        this.sevice_id = sevice_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getIncall_rate() {
        return incall_rate;
    }

    public void setIncall_rate(String incall_rate) {
        this.incall_rate = incall_rate;
    }

    public String getOutcall_rate() {
        return outcall_rate;
    }

    public void setOutcall_rate(String outcall_rate) {
        this.outcall_rate = outcall_rate;
    }
}
