package com.example.surya2;

import java.util.List;

public class StoreSearchResponseForMapping {

    private List<GoogleMapSearchResult> deliveryStores;
    private List<GoogleMapSearchResult> carryoutStores;
    private AddressInfoForSearch customerAddress;
    private String responseCode;

    public String getResponseCode() {
        return responseCode;
    }

    public List<GoogleMapSearchResult> getDeliveryStores() {
        return deliveryStores;
    }

    public List<GoogleMapSearchResult> getCarryoutStores() {
        return carryoutStores;
    }

    public AddressInfoForSearch getCustomerAddress() {
        return customerAddress;
    }

    public void setDeliveryStores(List<GoogleMapSearchResult> deliveryStores) {
        this.deliveryStores = deliveryStores;
    }

    public void setCarryoutStores(List<GoogleMapSearchResult> carryoutStores) {
        this.carryoutStores = carryoutStores;
    }

    public void setCustomerAddress(AddressInfoForSearch customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "StoreSearchResponseForMapping{" +
                "deliveryStores=" + deliveryStores +
                ", carryoutStores=" + carryoutStores +
                ", customerAddress=" + customerAddress +
                ", responseCode='" + responseCode + '\'' +
                '}';
    }
}
