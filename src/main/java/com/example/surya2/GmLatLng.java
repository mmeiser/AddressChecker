package com.example.surya2;

public class GmLatLng {

    private double latitude;
    private double longitude;

    public GmLatLng(){}

    public GmLatLng(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isValid() {
        return -90.0D <= this.latitude && this.latitude <= 90.0D && -180.0D <= this.longitude && this.longitude <= 180.0D;
    }

    @Override
    public String toString() {
        return "LatLng{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
