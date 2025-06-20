package com.suriyaprakhash.inventory_mgnt.inventory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for managing inventory operations.
 */
public interface InventoryService {

    /**
     * Add a product to inventory.
     *
     * @param inventoryData the inventory data to add
     * @return the added inventory data
     */
    InventoryData addToInventory(InventoryData inventoryData);

    /**
     * Get all inventory items.
     *
     * @return list of all inventory items
     */
    List<InventoryData> getAllInventoryItems();

    /**
     * Get an inventory item by its ID.
     *
     * @param id the inventory item ID
     * @return the inventory data if found, or empty optional if not found
     */
    Optional<InventoryData> getInventoryItemById(int id);

    /**
     * Get an inventory item by product ID.
     *
     * @param productId the product ID
     * @return the inventory data if found, or empty optional if not found
     */
    Optional<InventoryData> getInventoryItemByProductId(int productId);

    /**
     * Consume items from inventory.
     *
     * @param id the inventory item ID
     * @param quantity the quantity to consume
     * @return the updated inventory data if found and quantity is valid, or empty optional if not found or invalid quantity
     */
    Optional<InventoryData> consumeFromInventory(int id, int quantity);

    /**
     * Consume multiple items from inventory.
     *
     * @param productQuantityMap map of product IDs to quantities to consume
     * @return list of updated inventory data for successfully consumed items
     */
    List<InventoryData> consumeMultipleFromInventory(Map<Integer, Integer> productQuantityMap);

    /**
     * Remove an inventory item.
     *
     * @param id the inventory item ID to remove
     * @return true if the inventory item was removed, false if not found
     */
    boolean removeFromInventory(int id);
}
