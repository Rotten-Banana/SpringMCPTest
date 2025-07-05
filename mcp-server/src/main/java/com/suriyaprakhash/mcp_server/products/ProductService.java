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

	@Tool(name = "get_all_products",description = "It returns the name, type and cost of all the available products")
	public String getProducts() {
		List<ProductsData> productsDataList = restClient.get().uri("/api/products").retrieve().body(new ParameterizedTypeReference<List<ProductsData>>() {});
        assert productsDataList != null;
        return productsDataList
				.stream()
				.map(productsData -> String.format("""
					Product Id: %d,
					Name: %s
					Type: %s
					Price: %s
					""", productsData.id(), productsData.name(), productsData.type(), productsData.price()))
				.collect(Collectors.joining("\n"));
	}


	@Tool(name = "add_product",
			description = "Adds a new product to the product table. Requires the product's name and a valid price. Prompt for confirmation before adding the product")
//	public String addProduct(ProductsData productsData) { // this is not working (converting json to object) - instead use them as individual vars
	public String addProduct(String name, ProductType type, float price) {
		ProductsData productsData = new ProductsData(0, name, type, price);
		log.info("Attempting to add product. Raw input productsData: {}", productsData); // Add this line
        if (productsData.name() == null || productsData.name().isBlank()) {
			return "Product name is required to add a product.";
		}
		if (productsData.price() <= 0) {
			return "A valid price is required to add a product.";
		}
		log.info("Adding new product: {}", productsData);
		ProductsData productsDataFinal = restClient.post().uri("/api/products").body(productsData).retrieve().body(ProductsData.class);
		assert productsDataFinal != null;
		return productsDataFinal.toString();
	}


	// this is to test the functionality
	public static void main(String[] args) {
		ProductService client = new ProductService();
		System.out.println(client.getProducts());
	}

}