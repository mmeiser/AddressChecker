package com.example.surya2;

/**
 * Created by Surya_Bera on 5/21/2018.
 */
public class AnalysisResult {
    private String address;
    private String latLngMQ;
    private String latLngGM;
    private String foundInGM;
    private String storeIdMQ;
    private String storeIdGM;
    private String storeMatch;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatLngMQ() {
        return latLngMQ;
    }

    public void setLatLngMQ(String latLngMQ) {
        this.latLngMQ = latLngMQ;
    }

    public String getLatLngGM() {
        return latLngGM;
    }

    public void setLatLngGM(String latLngGM) {
        this.latLngGM = latLngGM;
    }

    public String getFoundInGM() {
        return foundInGM;
    }

    public void setFoundInGM(String foundInGM) {
        this.foundInGM = foundInGM;
    }

    public String getStoreIdMQ() {
        return storeIdMQ;
    }

    public void setStoreIdMQ(String storeIdMQ) {
        this.storeIdMQ = storeIdMQ;
    }

    public String getStoreIdGM() {
        return storeIdGM;
    }

    public void setStoreIdGM(String storeIdGM) {
        this.storeIdGM = storeIdGM;
    }

    public String getStoreMatch() {
        return storeMatch;
    }

    public void setStoreMatch(String storeMatch) {
        this.storeMatch = storeMatch;
    }
}
