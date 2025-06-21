package com.suriyaprakhash.mcp_server.products;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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


//	@Tool(name = "get_tool_by_id",description = "Get the product by given, it returns the name, type and cost")
//	public String getProductBy(String id) {
//		ProductsData productsData = restClient.get().uri("/api/products/{id}", id).retrieve().body(ProductsData.class);
//		return String.format("""
//					Name: %s
//					Type: %s
//					Price: %s
//					""", productsData.name(), productsData.type(), productsData.price());
//	}

	@Tool(name = "get_all_products",description = "It returns the name, type and cost of all the available products")
	public String getProducts() {
		List<ProductsData> productsDataList = restClient.get().uri("/api/products").retrieve().body(new ParameterizedTypeReference<List<ProductsData>>() {});
        assert productsDataList != null;
        return productsDataList
				.stream()
				.map(productsData -> String.format("""
					Name: %s
					Type: %s
					Price: %s
					""", productsData.name(), productsData.type(), productsData.price()))
				.collect(Collectors.joining("\n"));
	}


	@Tool(name = "add_product",description = "It adds a new product")
	public String addProduct(ProductsData productsData) {
//		if (productsData == null || productsData.name() == null || productsData.name().isBlank()) {
//			return "Product name is required to add a product.";
//		}
//		if (productsData.price() <= 0) {
//			return "A valid price is required to add a product.";
//		}
		log.info("Adding new product");
		ProductsData productsDataFinal = restClient.post().uri("/api/products").body(productsData).retrieve().body(ProductsData.class);
		assert productsDataFinal != null;
		return productsDataFinal.toString();
	}


	public static void main(String[] args) {
		ProductService client = new ProductService();
//		System.out.println(client.getProductBy("1"));
		System.out.println(client.getProducts());
	}

}