package com.example.kohendakanne.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItems implements Parcelable {
    private String restaurant_name;
    private String res_id;

    public MenuItems(String res_name, String res_id) {
        this.restaurant_name = res_name;
        this.res_id = res_id;
    }

    public MenuItems() {
    }

    protected MenuItems(Parcel in) {
        restaurant_name = in.readString();
        res_id = in.readString();
    }

    public static final Creator<MenuItems> CREATOR = new Creator<MenuItems>() {
        @Override
        public MenuItems createFromParcel(Parcel in) {
            return new MenuItems(in);
        }

        @Override
        public MenuItems[] newArray(int size) {
            return new MenuItems[size];
        }
    };

    public String getRes_name() {
        return restaurant_name;
    }

    public void setRes_name(String res_name) {
        this.restaurant_name = res_name;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurant_name);
        dest.writeString(res_id);
    }
}
