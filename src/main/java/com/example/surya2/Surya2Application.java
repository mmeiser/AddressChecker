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

@SpringBootApplication
public class Surya2Application {

	public static void main(String[] args) {
		SpringApplication.run(Surya2Application.class, args);
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


		    // address for this app need to be formatted as follows:
            //      a single string with pipe separators    14210 Plymouth Ave|55337|USA|Burnsville|24
            //          14210 Plymouth Ave  = street address   ( for both MQ and google)
            //          55337               = postal code      ( for both MQ and google )
            //          USA                 = country          ( for both MQ and google)
            //          Burnsville          = city              ( google only )
            //          24                  = territory id     ( google only)


			//String address= 14210 Plymouth Ave|55337|USA|Burnsville|24
			//String address = "1605 County Road 42 West|55306|USA|Burnsville|24";
			String address = "4345 Kit Carson Dr|40475|USA|Richmond|18";
			GISFactory myGISFactory = GISFactory.getInstance();

			GeocodeService geocodeServiceMQ =  myGISFactory.getGeocodeService();
			SearchService searchServiceMQ = myGISFactory.getSearchService();

			AnalysisTaskCallable atc = new AnalysisTaskCallable(address,geocodeServiceMQ, searchServiceMQ, restTemplate );
			AnalysisResult ar = atc.call();




		};
	}
}
