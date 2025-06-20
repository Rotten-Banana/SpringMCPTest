package com.suriyaprakhash.inventory_mgnt.products;

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
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products management API")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Create a new product", description = "Creates a new product with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductsData.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", 
                content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductsData> addProduct(
            @Parameter(description = "Product data to create", required = true) 
            @RequestBody ProductsData productData) {
        ProductsData savedData = productService.createProduct(productData);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductsData.class)))
    @GetMapping
    public ResponseEntity<List<ProductsData>> getAllProducts() {
        List<ProductsData> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Get product by ID", description = "Returns a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductsData.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductsData> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable("id") int id) {
        Optional<ProductsData> productOptional = productService.getProductById(id);

        return productOptional
            .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Update a product", description = "Updates a product with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductsData.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input data", 
                content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductsData> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable("id") int id, 
            @Parameter(description = "Updated product data", required = true)
            @RequestBody ProductsData productData) {
        Optional<ProductsData> updatedProductOptional = productService.updateProduct(id, productData);

        return updatedProductOptional
            .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable("id") int id) {
        boolean deleted = productService.deleteProduct(id);

        return deleted 
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
