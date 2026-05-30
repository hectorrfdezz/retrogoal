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

    /**
     * Filters products by the specified league. League matching is case-insensitive.
     *
     * @param league the league name to filter by
     * @return list of products belonging to that league
     */
    List<Product> filterByLeague(String league);

    /**
     * Filters products by whether they are retro (classic) shirts or current season shirts.
     *
     * @param retro true to return only retro shirts, false to return only current season shirts
     * @return list of products matching the retro flag
     */
    List<Product> filterByRetro(boolean retro);
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}