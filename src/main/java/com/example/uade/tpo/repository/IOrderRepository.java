package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long>{
    Order findByUserId(Long userId);
}
