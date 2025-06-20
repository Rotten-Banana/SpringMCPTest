package com.suriyaprakhash.inventory_mgnt.products;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing product operations.
 */
public interface ProductService {
    
    /**
     * Create a new product.
     *
     * @param productData the product data to create
     * @return the created product data
     */
    ProductsData createProduct(ProductsData productData);
    
    /**
     * Get all products.
     *
     * @return list of all products
     */
    List<ProductsData> getAllProducts();
    
    /**
     * Get a product by its ID.
     *
     * @param id the product ID
     * @return the product data if found, or empty optional if not found
     */
    Optional<ProductsData> getProductById(int id);
    
    /**
     * Update an existing product.
     *
     * @param id the product ID to update
     * @param productData the updated product data
     * @return the updated product data if found, or empty optional if not found
     */
    Optional<ProductsData> updateProduct(int id, ProductsData productData);
    
    /**
     * Delete a product by its ID.
     *
     * @param id the product ID to delete
     * @return true if the product was deleted, false if not found
     */
    boolean deleteProduct(int id);
}