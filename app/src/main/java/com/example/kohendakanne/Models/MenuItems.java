package com.example.kohendakanne.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItems {
    private String restaurant_id;
    private String price;
    private String meal_name;
    private String ingredients;
    private String image_url;

    public MenuItems() {
    }

    public MenuItems(String restaurant_id, String price, String meal_name, String ingredients, String image_url) {
        this.restaurant_id = restaurant_id;
        this.price = price;
        this.meal_name = meal_name;
        this.ingredients = ingredients;
        this.image_url = image_url;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
