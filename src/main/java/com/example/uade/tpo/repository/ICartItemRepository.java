package com.example.uade.tpo.repository;

import com.example.uade.tpo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long id);

    Optional<CartItem> findByCartIdAndProductId(Long id, Long productId);
}
