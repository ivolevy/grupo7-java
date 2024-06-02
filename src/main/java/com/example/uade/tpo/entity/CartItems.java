package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_seq")
    @SequenceGenerator(name = "cart_items_seq", sequenceName = "cart_items_seq", allocationSize = 1)
    private Long cartItemsId;
    @Column(name = "cart_id")
    private Long cartId;
    @Column(name = "product_id")
    private Long productId;
    @Column
    private Integer quantity;
}
