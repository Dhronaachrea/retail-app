package com.skilrock.retailapp.models.FieldX;

import androidx.annotation.NonNull;

public class RetailerLocationBean {
    private String id;
    private String location;
    private double lat;
    private double lon;

    @Override
    public String toString() {
        return "RetailerLocationBean{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
