package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String storeName;
    @Column
    private String address;

}
