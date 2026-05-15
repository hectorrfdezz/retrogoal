package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.OrderStatus;
import com.retrogoal.retrogoal.model.User;

import java.util.List;
import java.util.Map;
import com.retrogoal.retrogoal.model.Product;

public interface OrderService {
    Order createOrder(User user, Map<Product, Integer> cartItems, String shippingAddress, String paymentIntentId);
    List<Order> findOrdersByUser(User user);
    List<Order> findAllOrders();
    Order findById(Long id);
    Order updateStatus(Long id, OrderStatus status);
}