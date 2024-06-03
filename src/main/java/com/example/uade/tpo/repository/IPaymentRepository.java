package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);

    List<Long> getAllPaymentIds();
}
