package com.suriyaprakhash.inventory_mgnt.orders;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_date_time")
    private LocalDate orderDateTime;

    @Column(name = "total", nullable = false)
    private Float total;
}