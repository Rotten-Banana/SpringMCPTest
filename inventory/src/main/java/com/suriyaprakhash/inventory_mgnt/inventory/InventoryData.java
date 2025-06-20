package com.suriyaprakhash.inventory_mgnt.inventory;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Inventory data model")
record InventoryData(
    @Schema(description = "Unique identifier of the inventory item", example = "1") 
    int id, 
    
    @Schema(description = "ID of the product in inventory", example = "1") 
    int productId, 
    
    @Schema(description = "Available quantity of the product", example = "10") 
    int availability
) {
}