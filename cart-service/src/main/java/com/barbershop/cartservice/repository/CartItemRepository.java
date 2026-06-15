package com.barbershop.cartservice.repository;

import com.barbershop.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductId(Long productId);
}
