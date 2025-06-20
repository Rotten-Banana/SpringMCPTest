package com.suriyaprakhash.inventory_mgnt.inventory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pid", nullable = false)
    private Integer productId;

    @Column(name = "availability", nullable = false)
    private Integer availability;
}
