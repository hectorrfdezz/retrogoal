package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> searchByName(String name);
    List<Product> filterByTeam(String team);
    List<Product> filterByEra(String era);
    List<Product> filterBySize(String size);
    List<Product> filterByMaxPrice(BigDecimal price);
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}