package com.suriyaprakhash.inventory_mgnt.orders;

public record OrderLineItemData(
    Integer id,
    Integer productId,
    Integer orderId,
    Integer count
) {
}