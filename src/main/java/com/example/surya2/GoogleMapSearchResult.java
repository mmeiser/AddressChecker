package com.example.surya2;

public class GoogleMapSearchResult {

    private long storeId;
    private long distance;
    private long duration;
    private GeoCodedAddressForMapping geocodedAddress;
    private String originAddress;
    private String destinationAddress;
    private String readableDistance;
    private String readableDuration;

    public GoogleMapSearchResult() {
    }

    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public GeoCodedAddressForMapping getGeocodedAddress() {
        return geocodedAddress;
    }

    public void setGeocodedAddress(GeoCodedAddressForMapping geocodedAddress) {
        this.geocodedAddress = geocodedAddress;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getReadableDistance() {
        return readableDistance;
    }

    public void setReadableDistance(String readableDistance) {
        this.readableDistance = readableDistance;
    }

    public String getReadableDuration() {
        return readableDuration;
    }

    public void setReadableDuration(String readableDuration) {
        this.readableDuration = readableDuration;
    }

    @Override
    public String toString() {
        return "GoogleMapSearchResult{" +
                "storeId=" + storeId +
                ", distance=" + distance +
                ", duration=" + duration +
                ", geocodedAddress=" + geocodedAddress +
                ", originAddress='" + originAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", readableDistance='" + readableDistance + '\'' +
                ", readableDuration='" + readableDuration + '\'' +
                '}';
    }
}