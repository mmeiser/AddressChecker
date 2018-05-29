package com.example.surya2;


import com.papajohns.online.gis.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by Surya_Bera on 2/22/2018.
 */
public class MQService {

    static GISFactory gisFactory = GISFactory.getInstance();

    public static GeocodeResponse getGeoCodeResponse(String address, GeocodeService geocodeService) throws Exception{

        //GeocodeService geocodeService = gisFactory.getGeocodeService();
        String[] addressArray = address.split("\\|");

        String street = addressArray[0];
        String postal = addressArray[1];
        String country = addressArray[2];
        Address queryAddress = new Address();
        queryAddress.setStreet(StringUtils.trimToEmpty(street));
        queryAddress.setPostalCode(StringUtils.trimToEmpty(postal));
        queryAddress.setCountry(StringUtils.trimToEmpty(country));
        GeocodeResponse geocodeResponse = geocodeService.geocode(queryAddress);
        return geocodeResponse;
    }


    public static SearchResponse getSearchResponse(GeocodeResponse geocodeResponse, SearchService searchService) throws Exception{
        List<GeocodedAddress> geocodedAddressList = geocodeResponse.getGeocodeResults();
        GeocodedAddress geocodedAddress = geocodedAddressList.get(0);
        //SearchService searchService = gisFactory.getSearchService();
        SearchResponse searchResponse = searchService.getDelivery(geocodedAddress);

        return searchResponse;
    }

}
