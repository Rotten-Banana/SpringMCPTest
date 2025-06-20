package com.suriyaprakhash.inventory_mgnt.inventory;

import org.springframework.data.repository.CrudRepository;

interface InventoryRepository extends CrudRepository<InventoryEntity, Integer> {
    InventoryEntity findByProductId(Integer productId);
}