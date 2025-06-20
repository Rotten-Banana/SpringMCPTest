package com.suriyaprakhash.inventory_mgnt.inventory;

import java.util.List;

/**
 * Event that is published when multiple inventory items fall below 0.
 */
public class MultipleLowInventoryEvent {
    private final List<LowInventoryEvent> events;

    public MultipleLowInventoryEvent(List<LowInventoryEvent> events) {
        this.events = events;
    }

    public List<LowInventoryEvent> getEvents() {
        return events;
    }
}