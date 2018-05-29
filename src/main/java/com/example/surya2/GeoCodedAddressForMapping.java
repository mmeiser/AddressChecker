package com.example.surya2;

public class GeoCodedAddressForMapping {

    private GmLatLng latLng;

    public GmLatLng getLatLng() {
        return this.latLng;
    }

    public void setLatLng(GmLatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "GeoCodedAddressForMapping{" +
                "latLng=" + latLng +
                '}';
    }
}
