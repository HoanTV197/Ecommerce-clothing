package com.example.ecommerceclothing.repository;

import com.example.ecommerceclothing.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    // Find all shopping cart items for a user
    List<ShoppingCart> findByUserId(Long userId);

    // Find a specific cart item by user and product
    Optional<ShoppingCart> findByUserIdAndProductId(Long userId, Long productId);

    // Delete all items for a user after the order is placed
    void deleteByUserId(Long userId);
}
