package com.suriyaprakhash.inventory_mgnt.orders;

import com.suriyaprakhash.inventory_mgnt.inventory.LowInventoryEvent;
import com.suriyaprakhash.inventory_mgnt.inventory.MultipleLowInventoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Listener for low inventory events that creates orders automatically.
 */
@Component
public class LowInventoryEventListener {

    @Autowired
    private OrderService orderService;

    /**
     * Handle low inventory events by creating an order for the product.
     *
     * @param event the low inventory event
     */
    @EventListener
    public void handleLowInventoryEvent(LowInventoryEvent event) {

        // Create a new order with a single line item for the product
        List<OrderLineItemData> lineItems = new ArrayList<>();
        lineItems.add(new OrderLineItemData(
            null, // ID will be generated
            event.productId(),
            null, // Order ID will be set after order creation
             event.requestedQuantity()
        ));

        // Create the order
        OrderData orderData = new OrderData(
            null, // ID will be generated
            LocalDate.now(),
            0.0f, // Total will be calculated later based on product price
            lineItems
        );

        // Save the order
        orderService.createOrder(orderData);
    }


    @EventListener
    public void handleMultipleLowInventoryEvent(MultipleLowInventoryEvent event) {
        List<LowInventoryEvent> lowInventoryEvents = event.getEvents();
        List<OrderLineItemData> lineItems = lowInventoryEvents.stream().map(lowEvent -> {
            // Calculate the quantity to order (absolute value of negative inventory plus requested quantity)
            return new OrderLineItemData(
                    null, // ID will be generated
                    lowEvent.productId(),
                    null, // Order ID will be set after order creation
                    lowEvent.requestedQuantity()
            );
        }).toList();

        // Create the order
        OrderData orderData = new OrderData(
                null, // ID will be generated
                LocalDate.now(),
                0.0f, // Total will be calculated later based on product price
                lineItems
        );

        // Save the order
        orderService.createOrder(orderData);
    }
}
