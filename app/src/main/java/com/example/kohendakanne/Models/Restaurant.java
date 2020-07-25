package com.example.kohendakanne.Models;

import com.google.firebase.firestore.GeoPoint;

public class Restaurant {

    private String restaurant_name;
    private String district;
    private String image_url;
    private String restaurant_id;
    private GeoPoint GeoPoint;

    public Restaurant() {
    }

    public Restaurant(String restaurant_name, String district, String image_url, String restaurant_id, com.google.firebase.firestore.GeoPoint geoPoint) {
        this.restaurant_name = restaurant_name;
        this.district = district;
        this.image_url = image_url;
        this.restaurant_id = restaurant_id;
        GeoPoint = geoPoint;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public com.google.firebase.firestore.GeoPoint getGeoPoint() {
        return GeoPoint;
    }

    public void setGeoPoint(com.google.firebase.firestore.GeoPoint geoPoint) {
        GeoPoint = geoPoint;
    }
}
