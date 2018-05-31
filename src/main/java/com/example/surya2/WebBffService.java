package com.example.surya2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Surya_Bera on 5/18/2018.
 */
public class WebBffService {

    private static Logger LOGGER = LoggerFactory.getLogger(AddressCheckerApplication.class);


    public static StoreSearchResponseForMapping getStoreIdFromMSA(String address, RestTemplate restTemplate,
                                                String authString, String mappingUrl,
                                                boolean isAddressLoggingOn ) throws Exception {
        String[] addressArray = address.split("\\|");
        String street = addressArray[0];
        String postal = addressArray[1];
        String country = addressArray[2];
        String city = addressArray[3];
        String territoryId = addressArray[4];
        String storeId =   addressArray[5];

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
        searchedAddress.setDeliveryStoreId(storeId);


        customerAddress.setAddress(searchedAddress);
        request.setRadius(50);
        request.setSearchType("DELIVERY");
        request.setCustomerAddress(customerAddress);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String authEncodedString = new BASE64Encoder().encode(authString.getBytes());
        headers.add("Authorization", "Basic " + authEncodedString);
        HttpEntity<AddressDetailForSearchRequest> entity = new HttpEntity<AddressDetailForSearchRequest>(request, headers);


        ResponseEntity<StoreSearchResponseForMapping> response = restTemplate.postForEntity(mappingUrl, entity, StoreSearchResponseForMapping.class);
        StoreSearchResponseForMapping resp = null;
        resp = response.getBody();
        resp.getCustomerAddress().getAddress().setDeliveryStoreId(storeId);
        if ( isAddressLoggingOn ) {
            LOGGER.info("[getStoreIdFromMSA] address = " + address + " quality_code = " + response.getBody().getCustomerAddress().getQualityCode() + " response = " + response.getStatusCode());
        }

        return resp;
    }
}
