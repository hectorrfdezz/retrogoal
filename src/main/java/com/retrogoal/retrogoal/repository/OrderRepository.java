package com.retrogoal.retrogoal.repository;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Order> findByUser(User user);

    @Override
    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    List<Order> findAll();

    @Override
    @EntityGraph(attributePaths = {"user", "orderItems", "orderItems.product"})
    Optional<Order> findById(Long id);
}
