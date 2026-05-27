package com.retrogoal.retrogoal.service.impl;

import com.retrogoal.retrogoal.model.*;
import com.retrogoal.retrogoal.repository.OrderItemRepository;
import com.retrogoal.retrogoal.repository.OrderRepository;
import com.retrogoal.retrogoal.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order createOrder(User user, Map<Product, Integer> cartItems, String shippingAddress) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(shippingAddress);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice());
            items.add(item);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            // Reduce stock accordingly (optional). Ensure stock doesn't go negative.
            product.setStock(Math.max(product.getStock() - quantity, 0));
        }
        order.setTotalPrice(total);
        order.setOrderItems(items);
        Order saved = orderRepository.save(order);
        orderItemRepository.saveAll(items);
        return saved;
    }

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}