package com.example.surya2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.AddressComponent;
import static org.testng.Assert.assertTrue;

/*
    I had to add the google maps and junit dependencies to the pom for these tests to work
*/


@RunWith(SpringRunner.class)
@SpringBootTest
public class Surya2ApplicationTests {


	     final String myApiKey = "AIzaSyDm-OyrYBr-u7WsnXjk4FoFdckq-gqkqY0";



	        @Test
	        public void simpleGeocodeTest() {
	            String newuri = "https://maps.googleapis.com/maps/api/geocode/json?address=2002+Papa Johns+Blvd+Louisville,+KY";
	            GeocodingResult[] results = doGeocode(newuri);
	            if ( results != null ) {
	                Gson gson = new GsonBuilder().setPrettyPrinting().create();
	                System.out.println(gson.toJson(results[0].addressComponents));
	                loopThruGeocodingResultForfun(results);
	            }
	            assertTrue(results[0] != null);
	        }

	    @Test
	    public void geocodeBoundaryTest() {
	        String newuri = "https://maps.googleapis.com/maps/api/geocode/json?address=Winnetka@key=" + myApiKey;
	        //https:maps.googleapis.com/maps/api/geocode/json?address=Winnetka&key=YOUR_API_KEY
	        GeocodingResult[] results = doGeocode(newuri);
	        if (results != null && results.length > 0) {
	            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            System.out.println(gson.toJson(results[0].addressComponents));
	            loopThruGeocodingResultForfun(results);
	        }
	        assertTrue(results[0] != null);
	    }


	    public GeocodingResult[]  doGeocode(String uri) {
	        GeoApiContext context = new GeoApiContext.Builder().apiKey(myApiKey).build();

	           GeocodingResult[] results = null;
	           try {
	               results =  GeocodingApi.geocode(context,uri).await();

	           } catch ( Exception e) {
	               System.out.println(" error geocoding");
	           }
	           return results;

	    }

	    public GeocodingResult[]  doGeocodeWithClientId(String uri) {
	        String clientID = "pretendClientId";
	        String clientSecret = "pretendSecret";
	         GeoApiContext context = new GeoApiContext.Builder().enterpriseCredentials(clientID, clientSecret).build();

	            GeocodingResult[] results = null;
	            try {
	                results =  GeocodingApi.geocode(context,uri).await();
	            } catch ( Exception e) {
	                System.out.println(" error geocoding");
	            }
	            return results;

	     }


	    @Test
	     public void reversGeocodeTest () {
	        // this works
	        double lat = 40.714224;
	        double lng = -73.961452 ;
	        boolean testPassed = true;

	        //GET https://maps.google.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false&key=AIzaSyDm-OyrYBr-u7WsnXjk4FoFdckq-gqkqY0

	         HttpGet httpGet = new HttpGet("https://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false&key=" + myApiKey);
	         HttpClient client = new DefaultHttpClient();
	         HttpResponse response;
	         StringBuilder stringBuilder = new StringBuilder();
	         JSONObject jsonObject = new JSONObject();

	         try {
	             response = client.execute(httpGet);
	             HttpEntity entity = response.getEntity();
	             InputStream stream = entity.getContent();
	             int b;
	             while ((b = stream.read()) != -1) {
	                 stringBuilder.append((char) b);
	             }

	             jsonObject = new JSONObject(stringBuilder.toString());
	             // how to get the status of the json object
	             String status = jsonObject.optString("status");
	             if ( status.equalsIgnoreCase("OK")) {
	                  // here we are just outputting the returned Json results
	                  JSONObject location;
	                  String location_string;

	                   //Get JSON Array called "results" and then get the 0th complete object as JSON
	                   location = jsonObject.getJSONArray("results").getJSONObject(0);
	                   // Get the value of the attribute whose name is "formatted_string"
	                   location_string = location.getString("formatted_address");
	                   System.out.println(" formattted address:" + location_string);


	                  // this is just another example of how to parse stuff out of json objexts
	                  JSONObject location2 = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
	                  double lat1 = location2.getDouble("lat");
	                  double lng2 = location2.getDouble("lng");
	              } else {
	                  testPassed = false;
	              }


	         } catch (Exception e) {
	             testPassed = false;
	         }



	          assert(testPassed);
	    }

	    @Test
	    public void reverseGeocodeTest3() {
	        boolean testPassed = true;
	        Double latitude  = 40.714224;
	        Double longitude  = -73.961452 ;
	        LatLng latlng = new LatLng(latitude, longitude);
	        GeoApiContext context = new GeoApiContext.Builder().apiKey(myApiKey).build();

	        try {
	            final GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latlng).await();
	            if (results != null && results.length > 0) {
	                loopThruGeocodingResultForfun(results );
	            }
	        } catch (final Exception e) {
	            testPassed = false;
	         }

	        assert(testPassed);

	    }

	    public void loopThruGeocodingResultForfun( GeocodingResult[] results ) {
	        String myPostalCode = "";
	        String myCountry = "";
	        String myCity = "";
	        String myStreetNumber = "";
	        String myStreetAddr = "";

	        for(int i = 0; i< results.length; i++) {
	            String addr = results[i].formattedAddress;
	            System.out.println("formattedAddress = " + addr);
	            GeocodingResult result = results[i];
	            for (AddressComponent component : result.addressComponents) {
	                List<AddressComponentType> types = Arrays.asList(component.types);
	                if (types.contains(AddressComponentType.COUNTRY)) {
	                    myCountry = component.longName;
	                }
	                if (types.contains(AddressComponentType.LOCALITY)) {
	                    myCity = component.longName;
	                }
	                if (types.contains(AddressComponentType.POSTAL_CODE)) {
	                    myPostalCode = component.longName;
	                }
	                if (types.contains(AddressComponentType.STREET_NUMBER)) {
	                    myStreetNumber = component.longName;
	                }
	                if (types.contains(AddressComponentType.STREET_ADDRESS)) {
	                    myStreetAddr = component.longName;
	                }
	            }
	        }
	    }



}
