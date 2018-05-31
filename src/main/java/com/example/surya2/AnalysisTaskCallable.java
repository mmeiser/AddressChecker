package com.example.surya2;

import com.papajohns.online.gis.*;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Surya_Bera on 2/6/2018.
 */
public class AnalysisTaskCallable implements Callable<AnalysisResult> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisTaskCallable.class);

    private String address;
    private GeocodeService geocodeServiceMQ;
    private SearchService searchServiceMQ;
    private RestTemplate restTemplate;
    private String authString;
    private String mappingUrl;
    private boolean addressLoggingFlag;


    AnalysisTaskCallable(String address, GeocodeService geocodeServiceMQ, SearchService searchServiceMQ,
                         RestTemplate restTemplate, String authString , String mappingUrl, boolean addressLoggingFlag) {
        this.address = address;
        this.geocodeServiceMQ = geocodeServiceMQ;
        this.searchServiceMQ = searchServiceMQ;
        this.restTemplate = restTemplate;
        this.authString = authString;
        this.mappingUrl = mappingUrl;
        this.addressLoggingFlag = addressLoggingFlag;
    }

    public AnalysisResult call() throws Exception {
        AnalysisResult analysisResult = new AnalysisResult();
        String storeIdForMQ = "NotFound";
        String latLngMQ = "NotFound";
        String latLngGM = "NotFound";
        String storeIdFromMSA = "Not in GM";
        String inputStoreId = "";

        /*
        try {
            // call Mapquests service
            GeocodeResponse geocodeResponseMQ = MQService.getGeoCodeResponse(address, geocodeServiceMQ);
            if ("OK".equalsIgnoreCase(geocodeResponseMQ.getCode().name())) {
                String latMQ = ((Double) geocodeResponseMQ.getGeocodeResults().get(0).getLatLng().getLatitude()).toString();
                String lngMQ = ((Double) geocodeResponseMQ.getGeocodeResults().get(0).getLatLng().getLongitude()).toString();
                latLngMQ = latMQ + lngMQ;
                SearchResponse searchResponseForMQ = MQService.getSearchResponse(geocodeResponseMQ, searchServiceMQ);
                if (searchResponseForMQ.getResults() != null && searchResponseForMQ.getResults().get(SearchResultType.DELIVERY) != null &&
                        !searchResponseForMQ.getResults().get(SearchResultType.DELIVERY).isEmpty()) {
                    storeIdForMQ = searchResponseForMQ.getResults().get(SearchResultType.DELIVERY).get(0).getId();
                }
            } else if ("AMBIGUOUS_RESULTS".equalsIgnoreCase(geocodeResponseMQ.getCode().name())) {
                storeIdForMQ = "Ambiguous";
            } else {
                storeIdForMQ = "NotFound";
            }
        } catch (Exception ex) {
            storeIdForMQ = "Error";
            latLngMQ = "Error";
        }
        */

        //Call Google's MSA webbff getStoreDetails
        try {
            StoreSearchResponseForMapping msaResponse = WebBffService.getStoreIdFromMSA(address, restTemplate, authString, mappingUrl, addressLoggingFlag);
            if (msaResponse.getCustomerAddress() != null && msaResponse.getCustomerAddress().getGeocodedAddress() != null && msaResponse.getCustomerAddress().getGeocodedAddress().getLatLng() != null) {
                if (msaResponse.getCustomerAddress().getGeocodedAddress().getLatLng().getLatitude() != 0 && msaResponse.getCustomerAddress().getGeocodedAddress().getLatLng().getLongitude() != 0) {
                    latLngGM = new Double(msaResponse.getCustomerAddress().getGeocodedAddress().getLatLng().getLatitude()).toString() + new Double(msaResponse.getCustomerAddress().getGeocodedAddress().getLatLng().getLongitude()).toString();
                    storeIdFromMSA = "Not in TA database";
                }
            }

            try {
                inputStoreId = msaResponse.getCustomerAddress().getAddress().getDeliveryStoreId();
            } catch ( Exception e ) {
                inputStoreId = "";
            }

            if (msaResponse.getDeliveryStores() != null && !msaResponse.getDeliveryStores().isEmpty()) {
                storeIdFromMSA = ((Long) msaResponse.getDeliveryStores().get(0).getStoreId()).toString();
            }
        } catch (Exception ex) {
            storeIdFromMSA = "Error";
            latLngGM = "Error";
        }
        analysisResult.setAddress(address);
        analysisResult.setLatLngMQ(latLngMQ);
        analysisResult.setLatLngGM(latLngGM);
        analysisResult.setFoundInGM("NotFound".equalsIgnoreCase(latLngGM) ? "Not found in GM" : "Found in GM");
        analysisResult.setStoreIdMQ(storeIdForMQ);
        analysisResult.setStoreIdGM(storeIdFromMSA);
        analysisResult.setStoreMatch(storeIdForMQ.equalsIgnoreCase(storeIdFromMSA) ? "Store match" : "Different store");
        analysisResult.setInputStoreId(inputStoreId);
        return analysisResult;
      
    }



}
