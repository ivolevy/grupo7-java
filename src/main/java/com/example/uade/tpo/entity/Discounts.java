package com.example.uade.tpo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "discounts")
public class Discounts {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String code;
    @Column
    private String discountType;
    @Column
    private Integer discountValue;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
}
