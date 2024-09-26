package com.example.ecommerceclothing.controller;

import com.example.ecommerceclothing.model.Order;
import com.example.ecommerceclothing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitOrder(@RequestParam Long userId, @RequestParam String shippingAddress, @RequestParam String paymentMethod) {
        try {
            // Convert the payment method string to enum
            Order.PaymentMethod method = Order.PaymentMethod.valueOf(paymentMethod.toUpperCase());
            orderService.placeOrder(userId, shippingAddress, method);
            return ResponseEntity.ok("Order placed successfully");
        } catch (IllegalArgumentException e) {
            // Handle invalid payment method
            return ResponseEntity.badRequest().body("Invalid payment method: " + paymentMethod);
        }
    }
}
