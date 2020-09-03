package com.content.parser.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This is the application configuration. All the app configuration are read
 * from the application yml file. And can be modified without touching the
 * actual code.
 * 
 * <p>
 * The app has been configured with property yml <code>application.yml</code> for the <b>states</b> and <b>dateformat</b>.
 * More info can be added if needed.
 * </p>
 * 
 * @author suriya
 *
 */

//@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application")
public class ApplicationPropertiesConfig {

	// date format for the date of birth
	@Value("${application.date-format}")
	private String dateFormat;

	// map of state name against its abvr
	private Map<String, String> states = new HashMap<>();

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Map<String, String> getStates() {
		return states;
	}

	public void setStates(Map<String, String> states) {
		this.states = states;
	}

}