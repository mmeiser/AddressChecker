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
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.example.surya2.Utils.createStoreListCsv;

@SpringBootApplication
public class AddressCheckerApplication {
	private static Logger LOGGER = Logger.getLogger("");
	String inputFile = "C:\\util\\myNotes\\googleMaps\\addr2.csv";



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
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			/*  1. run this query
			select unique address1  ||  '|' || postal_code  ||  '|' || country ||   '|'  || city ||   '|'  || territory_id as address
			from pjus.order_header
			where order_type_code = 'D'
			and order_status_id = 2
			and ((store_id in ( 3615,55,275 ))  or  (postal_code in (  '65301', '40475', '40324' ) ))
			and trunc(business_date)  > '28-Feb-2018'     */

			//  2. export dataset .    delimited text  .  pipe delimeted...

			// 3. open it - remove header line

			// 4. make sure it is just 1 column ( view it in excel...  )

			// 5. manually replace any commas with a space and re-save  ... with notepad


		    // address for this app need to be formatted as follows:
            //      a single string with pipe separators    14210 Plymouth Ave|55337|USA|Burnsville|24
            //          14210 Plymouth Ave  = street address   ( for both MQ and google)
            //          55337               = postal code      ( for both MQ and google )
            //          USA                 = country          ( for both MQ and google)
            //          Burnsville          = city              ( google only )
            //          24                  = territory id     ( google only)


			//String address= 14210 Plymouth Ave|55337|USA|Burnsville|24
			//String address = "1605 County Road 42 West|55306|USA|Burnsville|24";
			//String address = "4345 Kit Carson Dr|40475|USA|Richmond|18";


			// read input file and loop thru it
			List<String> addressList = Utils.getAddressList(inputFile);
			GISFactory myGISFactory = GISFactory.getInstance();
			GeocodeService geocodeServiceMQ =  myGISFactory.getGeocodeService();
			SearchService searchServiceMQ = myGISFactory.getSearchService();
			List<String> addressListOutput = new ArrayList<String>();
			int cnt = 0;


			for ( String address : addressList) {
				AnalysisTaskCallable atc = new AnalysisTaskCallable(address, geocodeServiceMQ, searchServiceMQ, restTemplate);
				AnalysisResult ar = atc.call();
				if ( ar.getInputStoreId().equalsIgnoreCase(ar.getStoreIdGM()) ||
                        ar.getFoundInGM().equalsIgnoreCase("Found in GM")) {
					// do nothing
				} else {
					addressListOutput.add(address);
				}
				cnt++;
			}

			

            // this writes list of storeIds  that don't match or didn't geocode to output file
            createStoreListCsv( addressListOutput );
			LOGGER.info("finished");



		};
	}
}
