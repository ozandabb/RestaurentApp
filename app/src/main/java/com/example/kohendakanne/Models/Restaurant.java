package com.example.kohendakanne.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

public class Restaurant implements Parcelable {

    private String restaurant_name;
    private String district;
    private String image_url;
    private String restaurant_id;
    private String contact;

    private double latitude, longitude;
    private GeoPoint GeoPoint;

    public Restaurant() {
    }

    public Restaurant(String restaurant_name, String district, String image_url, String restaurant_id, String contact, com.google.firebase.firestore.GeoPoint geoPoint) {
        this.restaurant_name = restaurant_name;
        this.district = district;
        this.image_url = image_url;
        this.restaurant_id = restaurant_id;
        this.contact = contact;
        GeoPoint = geoPoint;
    }

    protected Restaurant(Parcel in) {
        restaurant_name = in.readString();
        district = in.readString();
        image_url = in.readString();
        restaurant_id = in.readString();
        contact = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        GeoPoint = new GeoPoint(latitude, longitude);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        latitude = getGeoPoint().getLatitude();
        longitude = getGeoPoint().getLongitude();
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);

    }
}
