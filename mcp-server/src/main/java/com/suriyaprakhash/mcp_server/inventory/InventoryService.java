package com.suriyaprakhash.mcp_server.inventory;

import com.suriyaprakhash.mcp_server.products.ProductType;
import com.suriyaprakhash.mcp_server.products.ProductsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryService {

    private static final String BASE_URL = "http://localhost:8080";

    private final RestClient restClient;

    public InventoryService() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("User-Agent", "ProductsApiClient/1.0 (contant@suriyaprakhash.com)")
                .build();
    }


    @Tool(name = "get_all_inventory_items",description = "It returns all the inventory items")
    public String getAllInventoryItems() {
        log.info("Getting all inventory items"); // Add this line
        List<InventoryData> inventoryItems = restClient.get().uri("/api/inventory").retrieve().body(new ParameterizedTypeReference<>() {});
        assert inventoryItems != null;
        return inventoryItems
                .stream()
                .map(inventoryItem -> String.format("""
					Inventory item id: %s
					Inventory Item availability: %s
					Product Id: %s
					""", inventoryItem.id(), inventoryItem.availability(), inventoryItem.productId()))
                .collect(Collectors.joining("\n"));
    }

    @Tool(name = "add_inventory_item",description = "Add a new product to the inventory item, if the product name is provided, then use the get all product tool to find the product id and then add it to the inventory")
    public String addInventoryItem(int productId, int availability) {
        InventoryData inventoryData = new InventoryData(0, productId, availability);
        log.info("Consuming inventory items"); // Add this line
        InventoryData inventoryDataResult = restClient.post().uri("/api/inventory").body(inventoryData).retrieve().body(InventoryData.class);
        assert inventoryDataResult != null;
        return String.format("""
					Inventory item id: %s
					Inventory Item availability: %s
					Product Id: %s
					""", inventoryDataResult.id(), inventoryDataResult.availability(), inventoryDataResult.productId());
    }

    @Tool(name = "consume_inventory_items",description = "Consume given items from the inventory")
    public String consumeItems(int inventoryId, int quantity) {
        log.info("Consuming inventory items"); // Add this line
        InventoryData inventoryDataResult = restClient.put().uri(
                uriBuilder -> uriBuilder
                        .path(String.format("/api/inventory/%d/consume", inventoryId))
                        .queryParam("quantity", quantity)
                        .build()
                ).retrieve().body(InventoryData.class);
        assert inventoryDataResult != null;
        return String.format("""
					Inventory item id: %s
					Inventory Item availability: %s
					Product Id: %s
					""", inventoryDataResult.id(), inventoryDataResult.availability(), inventoryDataResult.productId());
    }
}
