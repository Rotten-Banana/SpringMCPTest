package com.suriyaprakhash.mcp_server.products;

import com.fasterxml.jackson.annotation.JsonProperty;

record ProductsData(
    @JsonProperty("id")
    int id,
    @JsonProperty("name")
    String name,
    @JsonProperty("type")
    ProductType type,
    @JsonProperty("price")
    float price
) {
}