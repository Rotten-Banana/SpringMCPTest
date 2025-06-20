package com.suriyaprakhash.inventory_mgnt.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the InventoryService interface.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Maps an InventoryEntity to an InventoryData object.
     *
     * @param entity the entity to map
     * @return the mapped data object
     */
    private InventoryData mapEntityToData(InventoryEntity entity) {
        return new InventoryData(
            entity.getId(),
            entity.getProductId(),
            entity.getAvailability()
        );
    }

    /**
     * Maps an InventoryData to an InventoryEntity object.
     *
     * @param data the data to map
     * @param entity the entity to update (can be null for new entities)
     * @return the mapped entity object
     */
    private InventoryEntity mapDataToEntity(InventoryData data, InventoryEntity entity) {
        if (entity == null) {
            entity = new InventoryEntity();
        }
        entity.setProductId(data.productId());
        entity.setAvailability(data.availability());
        return entity;
    }

    @Override
    public InventoryData addToInventory(InventoryData inventoryData) {
        // Check if product already exists in inventory
        InventoryEntity existingEntity = inventoryRepository.findByProductId(inventoryData.productId());

        if (existingEntity != null) {
            // Update existing inventory item
            existingEntity.setAvailability(existingEntity.getAvailability() + inventoryData.availability());
            InventoryEntity savedEntity = inventoryRepository.save(existingEntity);
            return mapEntityToData(savedEntity);
        } else {
            // Create new inventory item
            InventoryEntity entity = mapDataToEntity(inventoryData, null);
            InventoryEntity savedEntity = inventoryRepository.save(entity);
            return mapEntityToData(savedEntity);
        }
    }

    @Override
    public List<InventoryData> getAllInventoryItems() {
        Iterable<InventoryEntity> entities = inventoryRepository.findAll();
        List<InventoryData> inventoryItems = new ArrayList<>();

        for (InventoryEntity entity : entities) {
            inventoryItems.add(mapEntityToData(entity));
        }

        return inventoryItems;
    }

    @Override
    public Optional<InventoryData> getInventoryItemById(int id) {
        Optional<InventoryEntity> entityOptional = inventoryRepository.findById(id);
        return entityOptional.map(this::mapEntityToData);
    }

    @Override
    public Optional<InventoryData> getInventoryItemByProductId(int productId) {
        InventoryEntity entity = inventoryRepository.findByProductId(productId);
        return entity != null ? Optional.of(mapEntityToData(entity)) : Optional.empty();
    }

    @Override
    public Optional<InventoryData> consumeFromInventory(int id, int quantity) {
        if (quantity <= 0) {
            return Optional.empty(); // Invalid quantity
        }

        Optional<InventoryEntity> entityOptional = inventoryRepository.findById(id);

        if (entityOptional.isPresent()) {
            InventoryEntity entity = entityOptional.get();

            // Check if inventory will fall below 0 after consumption
            if (entity.getAvailability() < quantity) {
                // Allow consumption but publish an event to trigger order placement
                int currentAvailability = entity.getAvailability();
                entity.setAvailability(currentAvailability - quantity);
                InventoryEntity updatedEntity = inventoryRepository.save(entity);

                // Publish event for low inventory
                LowInventoryEvent event = new LowInventoryEvent(
                    entity.getProductId(),
                    entity.getId(),
                    updatedEntity.getAvailability(),
                    quantity
                );
                eventPublisher.publishEvent(event);

                return Optional.of(mapEntityToData(updatedEntity));
            } else {
                // Normal consumption
                entity.setAvailability(entity.getAvailability() - quantity);
                InventoryEntity updatedEntity = inventoryRepository.save(entity);
                return Optional.of(mapEntityToData(updatedEntity));
            }
        }

        return Optional.empty(); // Inventory item not found
    }

    @Override
    public boolean removeFromInventory(int id) {
        Optional<InventoryEntity> entityOptional = inventoryRepository.findById(id);

        if (entityOptional.isPresent()) {
            inventoryRepository.deleteById(id);
            return true;
        }

        return false; // Inventory item not found
    }

    @Override
    public List<InventoryData> consumeMultipleFromInventory(Map<Integer, Integer> productQuantityMap) {
        List<InventoryData> updatedInventoryItems = new ArrayList<>();

        List<LowInventoryEvent> lowInventoryEvents = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : productQuantityMap.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            if (quantity <= 0) {
                continue; // Skip invalid quantities
            }

            InventoryEntity entity = inventoryRepository.findByProductId(productId);

            if (entity != null) {
                // Check if inventory will fall below 0 after consumption
                if (entity.getAvailability() < quantity) {
                    // Allow consumption but publish an event to trigger order placement
                    int currentAvailability = entity.getAvailability();
                    entity.setAvailability(currentAvailability - quantity);
                    InventoryEntity updatedEntity = inventoryRepository.save(entity);

                    // Publish event for low inventory
                    LowInventoryEvent event = new LowInventoryEvent(
                        entity.getProductId(),
                        entity.getId(),
                        updatedEntity.getAvailability(),
                        quantity
                    );

                    lowInventoryEvents.add(event);

                    updatedInventoryItems.add(mapEntityToData(updatedEntity));
                } else {
                    // Normal consumption
                    entity.setAvailability(entity.getAvailability() - quantity);
                    InventoryEntity updatedEntity = inventoryRepository.save(entity);
                    updatedInventoryItems.add(mapEntityToData(updatedEntity));
                }
            }
        }

        if (!CollectionUtils.isEmpty(lowInventoryEvents)) {
            eventPublisher.publishEvent(new MultipleLowInventoryEvent(lowInventoryEvents));
        }

        return updatedInventoryItems;
    }
}
