package com.example.surya2;

import com.papajohns.online.gis.GISFactory;
import com.papajohns.online.gis.GeocodeService;
import com.papajohns.online.gis.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static com.example.surya2.Utils.createStoreListCsv;

/*
  takes about 42 minutes in dev single threaded to process about 2750 addresses
  takes about 5 minites in dev to process 2750 addresses with 10 threads
 */
@SpringBootApplication
public class AddressCheckerApplication {
	private static Logger LOGGER = Logger.getLogger("");
	String inputFile = "C:\\util\\myNotes\\googleMaps\\addr2.csv";
    String outputFile = "C:\\util\\myNotes\\googleMaps\\storeDiff.csv";
    String outputFile2 = "C:\\util\\myNotes\\googleMaps\\googleNotFound.csv";
    GISFactory myGISFactory = GISFactory.getInstance();
    GeocodeService geocodeServiceMQ =  myGISFactory.getGeocodeService();
    SearchService searchServiceMQ = myGISFactory.getSearchService();
    RestTemplate restTemplate = new RestTemplate();
    final int THREAD_COUNT = 10;



	public static void main(String[] args) {
		SpringApplication.run(AddressCheckerApplication.class, args);
	}

	@Bean
		public RestTemplate restTemplate(RestTemplateBuilder builder) {
			return builder.build();
		}

		@Autowired
		RestOperations restOperations;

	@Bean
	public CommandLineRunner run(RestTemplate thisRestTemplate) throws Exception {
		return args -> {

			/*  1. run this query

            select unique address1  ||  '|' || postal_code  ||  '|' || country ||   '|'  || city ||   '|'  || territory_id ||   '|'  || store_id as address
            from pjus.order_header
            where order_type_code = 'D'
            and ((store_id in ( 3615, 55 ))  or  (postal_code in (  '65301' , '40475') ))
            and trunc(business_date)  > '01-Mar-2018'
            and location_type_id in ( 1,2)



			//  2. export dataset .    delimited text  .  pipe delimeted...     thru toad

			// 3.  make sure it is just 1 column ( view it in excel...  )

			// 4. open it - remove header line with notepad

			// 5. manually replace any commas with a space and re-save  ... with notepad


			//String address= 14210 Plymouth Ave|55337|USA|Burnsville|24
			//String address = "1605 County Road 42 West|55306|USA|Burnsville|24";
			//String address = "4345 Kit Carson Dr|40475|USA|Richmond|18";
		    // address for this app need to be formatted as follows:
            //      a single string with pipe separators    14210 Plymouth Ave|55337|USA|Burnsville|24
            //          14210 Plymouth Ave  = street address   ( for both MQ and google)
            //          55337               = postal code      ( for both MQ and google )
            //          USA                 = country          ( for both MQ and google)
            //          Burnsville          = city              ( google only )
            //          24                  = territory id     ( google only)
            //         1093                 = store_id in database    */

			

			// read input file and loop thru it
			List<String> addressList = Utils.getAddressList(inputFile);
			List<String> storeDiffOutputList = new ArrayList<String>();
			List<String> googleNotFoundOutputList = new ArrayList<String>();
			int matchCnt=0;
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(20000);
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(20000);
            List<Future<AnalysisResult>> futureList = new ArrayList<Future<AnalysisResult>>();


            for (String address : addressList) {
                  AnalysisTaskCallable testCallable = new AnalysisTaskCallable(address, geocodeServiceMQ, searchServiceMQ, restTemplate);
                  Future<AnalysisResult> future = executor.submit(testCallable);
                  futureList.add(future);
              }

              for (Future<AnalysisResult> future : futureList) {
                  AnalysisResult ar = future.get();
                  if ( ar.getFoundInGM().equalsIgnoreCase("Not found in GM") ) {
                        googleNotFoundOutputList.add(ar.getAddress() + " not found in Google" );
                    } else if (  !ar.getInputStoreId().equalsIgnoreCase(ar.getStoreIdGM() ) ) {
                        storeDiffOutputList.add(ar.getAddress() + " : " + ar.getStoreIdGM());
                    } else {
                        // do nothing  - it matches
                        matchCnt++;
                    }
              }

			

            // this writes list of storeIds  that don't match or didn't geocode to output file
            createStoreListCsv( storeDiffOutputList , outputFile);
			createStoreListCsv( googleNotFoundOutputList, outputFile2 );
			LOGGER.info("finished");

			// shut it down at it's convenience
            executor.shutdown();

		};
	}
}
