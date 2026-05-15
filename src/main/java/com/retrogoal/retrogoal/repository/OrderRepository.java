package com.retrogoal.retrogoal.repository;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}