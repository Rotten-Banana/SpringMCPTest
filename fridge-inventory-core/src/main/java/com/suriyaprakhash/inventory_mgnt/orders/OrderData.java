package com.suriyaprakhash.inventory_mgnt.orders;

import java.time.LocalDate;
import java.util.List;

public record OrderData(
    Integer id,
    LocalDate orderDateTime,
    List<OrderLineItemData> lineItems
) {
}
