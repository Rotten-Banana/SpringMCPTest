package com.suriyaprakhash.inventory_mgnt.orders;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing order operations.
 */
public interface OrderService {
    
    /**
     * Create a new order.
     *
     * @param orderData the order data to create
     * @return the created order data
     */
    OrderData createOrder(OrderData orderData);
    
    /**
     * Get all orders.
     *
     * @return list of all orders
     */
    List<OrderData> getAllOrders();
    
    /**
     * Get an order by its ID.
     *
     * @param id the order ID
     * @return the order data if found, or empty optional if not found
     */
    Optional<OrderData> getOrderById(int id);
    
    /**
     * Add a product to an order.
     *
     * @param orderId the order ID
     * @param productId the product ID
     * @param count the quantity to add
     * @return the updated order data if found, or empty optional if not found
     */
    Optional<OrderData> addProductToOrder(int orderId, int productId, int count);
}