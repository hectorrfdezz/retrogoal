package com.retrogoal.retrogoal.repository;

import com.retrogoal.retrogoal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByTeamContainingIgnoreCase(String team);
    List<Product> findByEraContainingIgnoreCase(String era);
    List<Product> findBySize(String size);
    List<Product> findByPriceLessThanEqual(BigDecimal price);
}