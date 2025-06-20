package com.suriyaprakhash.inventory_mgnt.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for order operations.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderData>> getAllOrders() {
        List<OrderData> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Get an order by its ID.
     *
     * @param id the order ID
     * @return the order if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderData> getOrderById(@PathVariable int id) {
        Optional<OrderData> orderOptional = orderService.getOrderById(id);
        return orderOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new order.
     *
     * @param orderData the order data to create
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<OrderData> createOrder(@RequestBody OrderData orderData) {
        OrderData createdOrder = orderService.createOrder(orderData);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Add a product to an order.
     *
     * @param orderId the order ID
     * @param productId the product ID
     * @param count the quantity to add
     * @return the updated order if found, or 404 if not found
     */
    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<OrderData> addProductToOrder(
            @PathVariable int orderId,
            @PathVariable int productId,
            @RequestParam int count) {
        
        Optional<OrderData> updatedOrderOptional = orderService.addProductToOrder(orderId, productId, count);
        
        return updatedOrderOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}