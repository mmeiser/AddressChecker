package com.example.surya2;

public class AddressDetailForSearchRequest {

    private AddressInfoForSearch customerAddress;
    private AddressInfoForSearch storeAddress;
    private long radius;
    private String searchType;

    public AddressInfoForSearch getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(AddressInfoForSearch customerAddress) {
        this.customerAddress = customerAddress;
    }

    public AddressInfoForSearch getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(AddressInfoForSearch storeAddress) {
        this.storeAddress = storeAddress;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        return "AddressDetailForSearchRequest{" +
                "customerAddress=" + customerAddress +
                ", storeAddress=" + storeAddress +
                ", radius=" + radius +
                ", searchType='" + searchType + '\'' +
                '}';
    }
}
