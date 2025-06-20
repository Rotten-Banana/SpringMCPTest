package com.suriyaprakhash.inventory_mgnt.products;

import org.springframework.data.repository.CrudRepository;

interface ProductRepository extends CrudRepository<ProductsEntity, Integer> {
}
