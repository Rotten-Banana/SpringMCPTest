package com.suriyaprakhash.inventory_mgnt.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the OrderService interface.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    /**
     * Maps an OrderEntity and its line items to an OrderData object.
     *
     * @param entity the entity to map
     * @param lineItems the line items to include
     * @return the mapped data object
     */
    private OrderData mapEntityToData(OrderEntity entity, List<OrderLineItemEntity> lineItems) {
        List<OrderLineItemData> lineItemsData = lineItems.stream()
                .map(this::mapLineItemEntityToData)
                .collect(Collectors.toList());

        return new OrderData(
                entity.getId(),
                entity.getOrderDateTime(),
                entity.getTotal(),
                lineItemsData
        );
    }

    /**
     * Maps an OrderLineItemEntity to an OrderLineItemData object.
     *
     * @param entity the entity to map
     * @return the mapped data object
     */
    private OrderLineItemData mapLineItemEntityToData(OrderLineItemEntity entity) {
        return new OrderLineItemData(
                entity.getId(),
                entity.getProductId(),
                entity.getOrderId(),
                entity.getCount()
        );
    }

    /**
     * Maps an OrderData to an OrderEntity object.
     *
     * @param data the data to map
     * @param entity the entity to update (can be null for new entities)
     * @return the mapped entity object
     */
    private OrderEntity mapDataToEntity(OrderData data, OrderEntity entity) {
        if (entity == null) {
            entity = new OrderEntity();
        }
        
        if (data.orderDateTime() == null) {
            entity.setOrderDateTime(LocalDate.now());
        } else {
            entity.setOrderDateTime(data.orderDateTime());
        }
        
        entity.setTotal(data.total());
        return entity;
    }

    @Override
    public OrderData createOrder(OrderData orderData) {
        OrderEntity entity = mapDataToEntity(orderData, null);
        OrderEntity savedEntity = orderRepository.save(entity);
        
        List<OrderLineItemEntity> lineItems = new ArrayList<>();
        
        if (orderData.lineItems() != null) {
            for (OrderLineItemData lineItemData : orderData.lineItems()) {
                OrderLineItemEntity lineItemEntity = new OrderLineItemEntity();
                lineItemEntity.setProductId(lineItemData.productId());
                lineItemEntity.setOrderId(savedEntity.getId());
                lineItemEntity.setCount(lineItemData.count());
                
                OrderLineItemEntity savedLineItem = orderLineItemRepository.save(lineItemEntity);
                lineItems.add(savedLineItem);
            }
        }
        
        return mapEntityToData(savedEntity, lineItems);
    }

    @Override
    public List<OrderData> getAllOrders() {
        List<OrderData> orders = new ArrayList<>();
        
        Iterable<OrderEntity> orderEntities = orderRepository.findAll();
        for (OrderEntity entity : orderEntities) {
            List<OrderLineItemEntity> lineItems = orderLineItemRepository.findByOrderId(entity.getId());
            orders.add(mapEntityToData(entity, lineItems));
        }
        
        return orders;
    }

    @Override
    public Optional<OrderData> getOrderById(int id) {
        Optional<OrderEntity> entityOptional = orderRepository.findById(id);
        
        if (entityOptional.isPresent()) {
            OrderEntity entity = entityOptional.get();
            List<OrderLineItemEntity> lineItems = orderLineItemRepository.findByOrderId(entity.getId());
            return Optional.of(mapEntityToData(entity, lineItems));
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<OrderData> addProductToOrder(int orderId, int productId, int count) {
        if (count <= 0) {
            return Optional.empty(); // Invalid count
        }
        
        Optional<OrderEntity> entityOptional = orderRepository.findById(orderId);
        
        if (entityOptional.isPresent()) {
            OrderEntity entity = entityOptional.get();
            
            OrderLineItemEntity lineItemEntity = new OrderLineItemEntity();
            lineItemEntity.setProductId(productId);
            lineItemEntity.setOrderId(orderId);
            lineItemEntity.setCount(count);
            
            OrderLineItemEntity savedLineItem = orderLineItemRepository.save(lineItemEntity);
            
            List<OrderLineItemEntity> lineItems = orderLineItemRepository.findByOrderId(orderId);
            
            return Optional.of(mapEntityToData(entity, lineItems));
        }
        
        return Optional.empty();
    }
}