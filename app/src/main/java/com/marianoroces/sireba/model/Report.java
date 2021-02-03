package com.marianoroces.sireba.model;

import java.util.Date;

public class Report {

    private int id;
    private Date date;
    private Category category;
    private String description;
    private String location;
    private Double locationLat;
    private Double locationLng;
    private String pictureURI;

    public Report(int id, Date date, Category category, String description, String location, Double locationLat, Double locationLng, String pictureURI) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.description = description;
        this.location = location;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.pictureURI = pictureURI;
    }

    public Report(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(Double locationLng) {
        this.locationLng = locationLng;
    }

    public String getPictureURI() {
        return pictureURI;
    }

    public void setPictureURI(String pictureURI) {
        this.pictureURI = pictureURI;
    }
}
