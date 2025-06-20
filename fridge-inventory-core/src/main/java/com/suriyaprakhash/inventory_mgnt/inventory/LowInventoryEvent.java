package com.suriyaprakhash.inventory_mgnt.inventory;

/**
 * Event that is published when inventory falls below 0.
 */
public record LowInventoryEvent(
    Integer productId,
    Integer inventoryId,
    Integer currentAvailability,
    Integer requestedQuantity
) {
}