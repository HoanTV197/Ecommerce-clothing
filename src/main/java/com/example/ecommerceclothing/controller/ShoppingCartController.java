package com.example.ecommerceclothing.controller;

import com.example.ecommerceclothing.model.ShoppingCart;
import com.example.ecommerceclothing.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        shoppingCartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    @GetMapping("/{userId}")
    public List<ShoppingCart> getCartItems(@PathVariable Long userId) {
        return shoppingCartService.getCartItems(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        shoppingCartService.updateCartItem(userId, productId, quantity);
        return ResponseEntity.ok("Cart item updated");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        shoppingCartService.removeFromCart(userId, productId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam Long userId) {
        shoppingCartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}
