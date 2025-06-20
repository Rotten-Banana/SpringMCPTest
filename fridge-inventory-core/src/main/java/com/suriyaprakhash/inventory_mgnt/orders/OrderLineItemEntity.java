package com.suriyaprakhash.inventory_mgnt.orders;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders_line_item")
@Getter
@Setter
public class OrderLineItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pid", nullable = false)
    private Integer productId;

    @Column(name = "oid", nullable = false)
    private Integer orderId;

    @Column(name = "count", nullable = false)
    private Integer count;
}