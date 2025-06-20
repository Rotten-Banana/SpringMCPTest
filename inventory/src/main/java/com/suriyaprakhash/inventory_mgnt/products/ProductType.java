package com.suriyaprakhash.inventory_mgnt.products;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of products available in the inventory")
enum ProductType {
    @Schema(description = "Meat products like chicken, beef, etc.")
    MEAT,

    @Schema(description = "Drink products like milk, juice, etc.")
    DRINK,

    @Schema(description = "Vegetable products like carrots, lettuce, etc.")
    VEGGIE,

    @Schema(description = "Desert products like ice cream, cake, etc.")
    DESERT
}
