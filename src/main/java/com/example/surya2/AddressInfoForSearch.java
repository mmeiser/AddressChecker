package com.example.surya2;

public class AddressInfoForSearch {

    private AddressForSearch address;
    private GeoCodedAddressForMapping geocodedAddress;
    private String qualityCode;

    public AddressForSearch getAddress() {
        return address;
    }

    public void setAddress(AddressForSearch address) {
        this.address = address;
    }

    public GeoCodedAddressForMapping getGeocodedAddress() {
        return geocodedAddress;
    }

    public void setGeocodedAddress(GeoCodedAddressForMapping geocodedAddress) {
        this.geocodedAddress = geocodedAddress;
    }

    public String getQualityCode() {
        return qualityCode;
    }

    public void setQualityCode(String qualityCode) {
        this.qualityCode = qualityCode;
    }

    @Override
    public String toString() {
        return "AddressInfoForSearch{" +
                "address=" + address +
                ", geocodedAddress=" + geocodedAddress +
                ", qualityCode=" + qualityCode +
                '}';
    }
}
