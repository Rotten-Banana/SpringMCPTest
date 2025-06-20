package com.suriyaprakhash.inventory_mgnt.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Component to initialize product data.
 */
@Component
public class ProductDataInitializer {

    @Autowired
    private ProductService productService;

    private Map<String, Integer> productIds = new HashMap<>();

    /**
     * Initialize sample product data.
     * 
     * @return a map of product names to their IDs
     */
    public Map<String, Integer> initializeProducts() {
        System.out.println("Initializing sample products...");

        createProduct("Milk", ProductType.DRINK, 2.99f);
        createProduct("Eggs", ProductType.MEAT, 3.49f);
        createProduct("Cheese", ProductType.DRINK, 4.99f);
        createProduct("Chicken", ProductType.MEAT, 7.99f);
        createProduct("Lettuce", ProductType.VEGGIE, 1.99f);
        createProduct("Ice Cream", ProductType.DESERT, 5.99f);

        System.out.println("Sample products initialized");
        return new HashMap<>(productIds);
    }

    /**
     * Helper method to create a product.
     * 
     * @param name the name of the product
     * @param type the type of the product
     * @param price the price of the product
     */
    private void createProduct(String name, ProductType type, float price) {
        ProductsData productData = new ProductsData(0, name, type, price);
        ProductsData createdProduct = productService.createProduct(productData);
        productIds.put(name, createdProduct.id());
        System.out.println("Created product: " + name + " with ID: " + createdProduct.id());
    }
}