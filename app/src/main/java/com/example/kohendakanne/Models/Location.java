package com.example.kohendakanne.Models;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Location {
    private GeoPoint geo_Point;
    private @ServerTimestamp Date timestamp;

    public Location() {
    }

    public Location(GeoPoint geo_Point, Date timestamp) {
        this.geo_Point = geo_Point;
        this.timestamp = timestamp;
    }

    public GeoPoint getGeo_Point() {
        return geo_Point;
    }

    public void setGeo_Point(GeoPoint geo_Point) {
        this.geo_Point = geo_Point;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
