package com.example.ecommerceclothing.service;

import com.example.ecommerceclothing.model.Product;
import com.example.ecommerceclothing.model.ShoppingCart;
import com.example.ecommerceclothing.model.User;
import com.example.ecommerceclothing.repository.ProductRepository;
import com.example.ecommerceclothing.repository.ShoppingCartRepository;
import com.example.ecommerceclothing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Add item to cart
    public ShoppingCart addToCart(Long userId, Long productId, int quantity) {
        // Fetch user and product entities
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the item already exists in the cart
        Optional<ShoppingCart> existingItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem.isPresent()) {
            // Update quantity if item already exists
            ShoppingCart cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return shoppingCartRepository.save(cartItem);
        } else {
            // Create a new cart item
            ShoppingCart newItem = new ShoppingCart();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            return shoppingCartRepository.save(newItem);
        }
    }

    // Get cart items for a user
    public List<ShoppingCart> getCartItems(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    // Update quantity of an item in the cart
    public ShoppingCart updateCartItem(Long userId, Long productId, int quantity) {
        ShoppingCart cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cartItem.setQuantity(quantity);
        return shoppingCartRepository.save(cartItem);
    }

    // Remove item from cart
    public void removeFromCart(Long userId, Long productId) {
        ShoppingCart cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        shoppingCartRepository.delete(cartItem);
    }

    // Clear entire cart
    public void clearCart(Long userId) {
        shoppingCartRepository.deleteByUserId(userId);
    }
}
