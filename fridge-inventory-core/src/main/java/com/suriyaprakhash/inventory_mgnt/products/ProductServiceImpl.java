package com.suriyaprakhash.inventory_mgnt.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the ProductService interface.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Maps a ProductsEntity to a ProductsData object.
     *
     * @param entity the entity to map
     * @return the mapped data object
     */
    private ProductsData mapEntityToData(ProductsEntity entity) {
        return new ProductsData(
            entity.getId(),
            entity.getName(),
            ProductType.valueOf(entity.getType()),
            entity.getPrice().floatValue()
        );
    }

    /**
     * Maps a ProductsData to a ProductsEntity object.
     *
     * @param data the data to map
     * @param entity the entity to update (can be null for new entities)
     * @return the mapped entity object
     */
    private ProductsEntity mapDataToEntity(ProductsData data, ProductsEntity entity) {
        if (entity == null) {
            entity = new ProductsEntity();
        }
        entity.setName(data.name());
        entity.setType(data.type().toString());
        entity.setPrice(Double.valueOf(data.price()));
        return entity;
    }

    @Override
    public ProductsData createProduct(ProductsData productData) {
        ProductsEntity entity = mapDataToEntity(productData, null);
        ProductsEntity savedEntity = productRepository.save(entity);
        return mapEntityToData(savedEntity);
    }

    @Override
    public List<ProductsData> getAllProducts() {
        Iterable<ProductsEntity> entities = productRepository.findAll();
        List<ProductsData> products = new ArrayList<>();

        for (ProductsEntity entity : entities) {
            products.add(mapEntityToData(entity));
        }

        return products;
    }

    @Override
    public Optional<ProductsData> getProductById(int id) {
        Optional<ProductsEntity> entityOptional = productRepository.findById(id);
        return entityOptional.map(this::mapEntityToData);
    }

    @Override
    public Optional<ProductsData> updateProduct(int id, ProductsData productData) {
        Optional<ProductsEntity> entityOptional = productRepository.findById(id);
        
        if (entityOptional.isPresent()) {
            ProductsEntity entity = entityOptional.get();
            entity = mapDataToEntity(productData, entity);
            ProductsEntity updatedEntity = productRepository.save(entity);
            return Optional.of(mapEntityToData(updatedEntity));
        }
        
        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(int id) {
        Optional<ProductsEntity> entityOptional = productRepository.findById(id);
        
        if (entityOptional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        
        return false;
    }
}