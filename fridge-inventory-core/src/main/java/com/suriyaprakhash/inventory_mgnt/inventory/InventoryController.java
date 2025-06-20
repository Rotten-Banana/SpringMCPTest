package com.suriyaprakhash.inventory_mgnt.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Inventory management API")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Operation(summary = "Add item to inventory", description = "Adds a new item to inventory or updates existing item quantity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item added to inventory successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = InventoryData.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", 
                content = @Content)
    })
    @PostMapping
    public ResponseEntity<InventoryData> addToInventory(
            @Parameter(description = "Inventory data to add", required = true) 
            @RequestBody InventoryData inventoryData) {
        InventoryData savedData = inventoryService.addToInventory(inventoryData);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all inventory items", description = "Returns a list of all inventory items")
    @ApiResponse(responseCode = "200", description = "List of inventory items retrieved successfully",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = InventoryData.class)))
    @GetMapping
    public ResponseEntity<List<InventoryData>> getAllInventoryItems() {
        List<InventoryData> inventoryItems = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @Operation(summary = "Get inventory item by ID", description = "Returns an inventory item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventory item found",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = InventoryData.class))),
        @ApiResponse(responseCode = "404", description = "Inventory item not found", 
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryData> getInventoryItemById(
            @Parameter(description = "ID of the inventory item to retrieve", required = true)
            @PathVariable("id") int id) {
        Optional<InventoryData> inventoryOptional = inventoryService.getInventoryItemById(id);

        return inventoryOptional
            .map(inventory -> new ResponseEntity<>(inventory, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get inventory item by product ID", description = "Returns an inventory item by product ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventory item found",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = InventoryData.class))),
        @ApiResponse(responseCode = "404", description = "Inventory item not found", 
                content = @Content)
    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryData> getInventoryItemByProductId(
            @Parameter(description = "Product ID to retrieve inventory for", required = true)
            @PathVariable("productId") int productId) {
        Optional<InventoryData> inventoryOptional = inventoryService.getInventoryItemByProductId(productId);

        return inventoryOptional
            .map(inventory -> new ResponseEntity<>(inventory, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Consume items from inventory", description = "Reduces the availability of an item in inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Items consumed successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = InventoryData.class))),
        @ApiResponse(responseCode = "400", description = "Invalid quantity or not enough items available", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Inventory item not found", 
                content = @Content)
    })
    @PutMapping("/{id}/consume")
    public ResponseEntity<InventoryData> consumeFromInventory(
            @Parameter(description = "ID of the inventory item to consume from", required = true)
            @PathVariable("id") int id,
            @Parameter(description = "Quantity to consume", required = true)
            @RequestParam("quantity") int quantity) {
        Optional<InventoryData> updatedInventoryOptional = inventoryService.consumeFromInventory(id, quantity);

        return updatedInventoryOptional
            .map(inventory -> new ResponseEntity<>(inventory, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Consume multiple items from inventory", description = "Reduces the availability of multiple items in inventory based on product IDs and quantities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items consumed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryData.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)
    })
    @PutMapping("/consume")
    public ResponseEntity<List<InventoryData>> consumeMultipleFromInventory(
            @Parameter(description = "Map of product IDs to quantities to consume", required = true)
            @RequestBody Map<Integer, Integer> productQuantityMap) {
        List<InventoryData> updatedInventory = inventoryService.consumeMultipleFromInventory(productQuantityMap);

        if (updatedInventory.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
    }

    @Operation(summary = "Remove item from inventory", description = "Removes an item from inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item removed successfully", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Inventory item not found", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> removeFromInventory(
            @Parameter(description = "ID of the inventory item to remove", required = true)
            @PathVariable("id") int id) {
        boolean removed = inventoryService.removeFromInventory(id);

        return removed 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
