package com.suriyaprakhash.inventory_mgnt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.suriyaprakhash.inventory_mgnt.inventory.InventoryDataInitializer;
import com.suriyaprakhash.inventory_mgnt.products.ProductDataInitializer;

import java.util.Map;

/**
 * Component to initialize sample data for the application.
 * This class coordinates the initialization of product and inventory data on application startup.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductDataInitializer productDataInitializer;

    @Autowired
    private InventoryDataInitializer inventoryDataInitializer;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting sample data initialization...");
        
        // Initialize products first
        Map<String, Integer> productIds = productDataInitializer.initializeProducts();
        
        // Then initialize inventory with the product IDs
        inventoryDataInitializer.initializeInventory(productIds);
        
        System.out.println("Sample data initialization complete");
    }
}