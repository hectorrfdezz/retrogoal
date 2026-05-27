package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.OrderStatus;
import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.User;

import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * Creates a new order using the items in the cart.
     */
    Order createOrder(User user, Map<Product, Integer> cartItems, String shippingAddress);
    List<Order> findOrdersByUser(User user);
    List<Order> findAllOrders();
    Order findById(Long id);
    Order updateStatus(Long id, OrderStatus status);
}
