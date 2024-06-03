package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long>{
    Cart findByUserId(Long userId);
}
