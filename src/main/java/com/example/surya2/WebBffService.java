package com.example.surya2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

/**
 * Created by Surya_Bera on 5/18/2018.
 */
public class WebBffService {

    final static String myApiKey = "AIzaSyDm-OyrYBr-u7WsnXjk4FoFdckq-gqkqY0";


    public static StoreSearchResponseForMapping getStoreIdFromMSA(String address, RestTemplate restTemplate) throws Exception {
        String[] addressArray = address.split("\\|");
        String street = addressArray[0];
        String postal = addressArray[1];
        String country = addressArray[2];
        String city = addressArray[3];
        String territoryId = addressArray[4];   

        AddressDetailForSearchRequest request = new AddressDetailForSearchRequest();
        AddressInfoForSearch customerAddress = new AddressInfoForSearch();
        AddressForSearch searchedAddress = new AddressForSearch();
        searchedAddress.setAddress1(street);
        searchedAddress.setAddress2("");
        searchedAddress.setAptCode("NON");
        searchedAddress.setCity(city);
        searchedAddress.setLocationType("RE");
        searchedAddress.setPostalCode(postal);
        searchedAddress.setTerritoryId(Integer.parseInt(territoryId));
        searchedAddress.setStreetSideId(0);

        customerAddress.setAddress(searchedAddress);
        request.setRadius(50);
        request.setSearchType("DELIVERY");
        request.setCustomerAddress(customerAddress);

        StoreSearchResponseForMapping resp = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String authString = "online" + ":" + "c1529e56-0b47-4ca2-bf4c-5b5ebac0164f";
        String authEncodedString = new BASE64Encoder().encode(authString.getBytes());
        headers.add("Authorization", "Basic " + authEncodedString);
        HttpEntity<AddressDetailForSearchRequest> entity = new HttpEntity<AddressDetailForSearchRequest>(request, headers);
        String mappingUrl = "https://mapping-dev1.papajohns.com/webbff/api/v2/getStoreDetails";
        //String mappingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=Winnetka@key=" + myApiKey;


        ResponseEntity<StoreSearchResponseForMapping> response = restTemplate.postForEntity(mappingUrl, entity, StoreSearchResponseForMapping.class);
        resp = response.getBody();


        return resp;
    }
}
