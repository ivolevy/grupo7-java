package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_discounts")
public class OrderDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_discounts_seq")
    @SequenceGenerator(name = "order_discounts_seq", sequenceName = "order_discounts_seq", allocationSize = 1)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "discount_id")
    private Long discountId;
    @Column
    private Double amount;
}
