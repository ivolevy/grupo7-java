package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount, Long> {
    boolean existsByCode(String code);

    void deleteByCode(String code);
}
