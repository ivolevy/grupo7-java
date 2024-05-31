package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
        @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
        private Long id;
        @Column(name = "user_id", nullable = false)
        private Long userId;
        @Column
        private Integer quantity;
        @Column(name = "order_date")
        private Date orderDate;
        @Column
        private String status;
}
