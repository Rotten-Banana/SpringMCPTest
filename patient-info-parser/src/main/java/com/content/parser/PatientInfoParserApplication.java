package com.content.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.content.parser.config.ApplicationPropertiesConfig;

/**
 * This is an spring boot application, for parsing the xml input to a json
 * output format. This is scalable and independent to handle multiple such
 * request.
 * 
 * Refer {@link com.content.parser.controller.PatientController} for more
 * information on the endpoint
 * 
 * <b>Note:</b>Not attaching a README.md since will be sharing the project as a zip
 * 
 * <p>
 * The application is exposed via a swagger ui where the xml data can be posted.
 * @see <a href="localhost:8080/swagger-ui.html">Swagger</a>
 * or the problem can be accessed thru a postman client or a similar app with the xml request body
 * @see <a href="localhost:8080/parse">Parse data</a>
 * </p>
 * 
 * <p>
 * The application has configuration in the application.yml
 * {@link ApplicationPropertiesConfig}
 * </p>
 * 
 * <p>
 * A unit test case sample is available for the service just a basic one
 * for the valid condition check
 * </p>
 *      
 *      
 * @author suriya
 *
 */
@SpringBootApplication
public class PatientInfoParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientInfoParserApplication.class, args);
	}
}
