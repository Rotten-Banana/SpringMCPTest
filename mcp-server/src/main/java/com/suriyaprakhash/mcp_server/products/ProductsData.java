package com.suriyaprakhash.mcp_server.products;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product data model")
record ProductsData(
    @Schema(description = "Unique identifier of the product", example = "1") 
    int id, 

    @Schema(description = "Name of the product", example = "Milk") 
    String name, 

    @Schema(description = "Type of the product", example = "DRINK") 
    ProductType type, 

    @Schema(description = "Price of the product", example = "2.99") 
    float price
) {
}