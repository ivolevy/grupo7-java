package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
