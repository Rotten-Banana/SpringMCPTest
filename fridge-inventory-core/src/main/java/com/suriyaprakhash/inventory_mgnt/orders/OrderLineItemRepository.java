package com.suriyaprakhash.inventory_mgnt.orders;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineItemRepository extends CrudRepository<OrderLineItemEntity, Integer> {
    List<OrderLineItemEntity> findByOrderId(Integer orderId);
}