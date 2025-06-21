package com.suriyaprakhash.mcp_server.products;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ProductService {

	private static final String BASE_URL = "http://localhost:8080";

	private final RestClient restClient;

	public ProductService() {

		this.restClient = RestClient.builder()
			.baseUrl(BASE_URL)
			.defaultHeader("Accept", "application/json")
			.defaultHeader("User-Agent", "ProductsApiClient/1.0 (contant@suriyaprakhash.com)")
			.build();
	}

//	@JsonIgnoreProperties(ignoreUnknown = true)
//	public record Points(@JsonProperty("properties") Props properties) {
//		@JsonIgnoreProperties(ignoreUnknown = true)
//		public record Props(@JsonProperty("forecast") String forecast) {
//		}
//	}
//
//	@JsonIgnoreProperties(ignoreUnknown = true)
//	public record Forecast(@JsonProperty("properties") Props properties) {
//		@JsonIgnoreProperties(ignoreUnknown = true)
//		public record Props(@JsonProperty("periods") List<Period> periods) {
//		}
//
//		@JsonIgnoreProperties(ignoreUnknown = true)
//		public record Period(@JsonProperty("number") Integer number, @JsonProperty("name") String name,
//				@JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
//				@JsonProperty("isDaytime") Boolean isDayTime, @JsonProperty("temperature") Integer temperature,
//				@JsonProperty("temperatureUnit") String temperatureUnit,
//				@JsonProperty("temperatureTrend") String temperatureTrend,
//				@JsonProperty("probabilityOfPrecipitation") Map probabilityOfPrecipitation,
//				@JsonProperty("windSpeed") String windSpeed, @JsonProperty("windDirection") String windDirection,
//				@JsonProperty("icon") String icon, @JsonProperty("shortForecast") String shortForecast,
//				@JsonProperty("detailedForecast") String detailedForecast) {
//		}
//	}
//
//	@JsonIgnoreProperties(ignoreUnknown = true)
//	public record Alert(@JsonProperty("features") List<Feature> features) {
//
//		@JsonIgnoreProperties(ignoreUnknown = true)
//		public record Feature(@JsonProperty("properties") Properties properties) {
//		}
//
//		@JsonIgnoreProperties(ignoreUnknown = true)
//		public record Properties(@JsonProperty("event") String event, @JsonProperty("areaDesc") String areaDesc,
//				@JsonProperty("severity") String severity, @JsonProperty("description") String description,
//				@JsonProperty("instruction") String instruction) {
//		}
//	}

//	/**
//	 * Get forecast for a specific latitude/longitude
//	 * @param latitude Latitude
//	 * @param longitude Longitude
//	 * @return The forecast for the given location
//	 */
//	@Tool(description = "Get weather forecast for a specific latitude/longitude")
//	public String getWeatherForecastByLocation(double latitude, double longitude) {
//
//		var points = restClient.get()
//			.uri("/points/{latitude},{longitude}", latitude, longitude)
//			.retrieve()
//			.body(Points.class);
//
//		var forecast = restClient.get().uri(points.properties().forecast()).retrieve().body(Forecast.class);
//
//		String forecastText = forecast.properties().periods().stream().map(p -> {
//			return String.format("""
//					%s:
//					Temperature: %s %s
//					Wind: %s %s
//					Forecast: %s
//					""", p.name(), p.temperature(), p.temperatureUnit(), p.windSpeed(), p.windDirection(),
//					p.detailedForecast());
//		}).collect(Collectors.joining());
//
//		return forecastText;
//	}


//	@Tool(description = "Get weather alerts for a US state. Input is Two-letter US state code (e.g. CA, NY)")
//	public String getAlerts(String state) {
//		Alert alert = restClient.get().uri("/alerts/active/area/{state}", state).retrieve().body(Alert.class);
//
//		return alert.features()
//			.stream()
//			.map(f -> String.format("""
//					Event: %s
//					Area: %s
//					Severity: %s
//					Description: %s
//					Instructions: %s
//					""", f.properties().event(), f.properties.areaDesc(), f.properties.severity(),
//					f.properties.description(), f.properties.instruction()))
//			.collect(Collectors.joining("\n"));
//	}

	@Tool(name = "get_tool_by_id",description = "Get the tool by given")
	public String getProductBy(String id) {
		ProductsData productsData = restClient.get().uri("/api/products/{id}", id).retrieve().body(ProductsData.class);
		return productsData.name();
	}

	@Tool(name = "get_all_products",description = "Get all the available products")
	public String getProducts() {
		List<ProductsData> productsData = restClient.get().uri("/api/products").retrieve().body(new ParameterizedTypeReference<List<ProductsData>>() {});
        assert productsData != null;
        return productsData.toString();
	}


	public static void main(String[] args) {
		ProductService client = new ProductService();
//		System.out.println(client.getWeatherForecastByLocation(47.6062, -122.3321));
		System.out.println(client.getProductBy("1"));
		System.out.println(client.getProducts());
	}

}