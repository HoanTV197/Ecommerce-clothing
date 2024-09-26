package com.example.ecommerceclothing.service;

import com.example.ecommerceclothing.model.Order;
import com.example.ecommerceclothing.model.OrderItem;
import com.example.ecommerceclothing.model.ShoppingCart;
import com.example.ecommerceclothing.repository.OrderRepository;
import com.example.ecommerceclothing.repository.OrderItemRepository;
import com.example.ecommerceclothing.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Order placeOrder(Long userId, String shippingAddress, Order.PaymentMethod paymentMethod) {
        // Retrieve the shopping cart items for the user
        List<ShoppingCart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items in the cart");
        }

        // Calculate the total order amount
        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create a new Order
        Order order = new Order();
        order.setUser(cartItems.get(0).getUser());  // Set the user
        order.setTotal(total);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(Order.Status.PENDING);

        // Save the order to the database
        Order savedOrder = orderRepository.save(order);

        // Add order items from the cart
        for (ShoppingCart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            // Save each order item
            orderItemRepository.save(orderItem);
        }

        // Clear the shopping cart after placing the order
        cartRepository.deleteByUserId(userId);

        return savedOrder;
    }
}
