package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_methods_seq")
    @SequenceGenerator(name = "payment_methods_seq", sequenceName = "payment_methods_seq", allocationSize = 1)
    private Long paymentMethodId;
    @Column
    private String name;
}
