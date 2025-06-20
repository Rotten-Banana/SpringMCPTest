package com.suriyaprakhash.inventory_mgnt.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Component to initialize inventory data.
 */
@Component
public class InventoryDataInitializer {

    @Autowired
    private InventoryService inventoryService;

    /**
     * Initialize sample inventory data.
     * 
     * @param productIds a map of product names to their IDs
     */
    public void initializeInventory(Map<String, Integer> productIds) {
        System.out.println("Initializing sample inventory...");

        addToInventory(productIds.get("Milk"), 10);
        addToInventory(productIds.get("Eggs"), 24);
        addToInventory(productIds.get("Cheese"), 5);
        addToInventory(productIds.get("Chicken"), 3);
        addToInventory(productIds.get("Lettuce"), 7);
        addToInventory(productIds.get("Ice Cream"), 2);

        System.out.println("Sample inventory initialized");
    }

    /**
     * Helper method to add a product to inventory.
     * 
     * @param productId the ID of the product
     * @param quantity the quantity to add
     */
    private void addToInventory(Integer productId, int quantity) {
        if (productId != null) {
            InventoryData inventoryData = new InventoryData(0, productId, quantity);
            inventoryService.addToInventory(inventoryData);
            System.out.println("Added " + quantity + " of product ID " + productId + " to inventory");
        } else {
            System.out.println("Product ID is null, cannot add to inventory");
        }
    }
}