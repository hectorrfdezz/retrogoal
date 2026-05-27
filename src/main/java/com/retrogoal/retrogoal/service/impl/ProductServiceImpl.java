package com.retrogoal.retrogoal.service.impl;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.repository.ProductRepository;
import com.retrogoal.retrogoal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCaseOrNameEnContainingIgnoreCaseOrNameFrContainingIgnoreCase(name, name, name);
    }

    @Override
    public List<Product> filterByTeam(String team) {
        return productRepository.findByTeamContainingIgnoreCaseOrTeamEnContainingIgnoreCaseOrTeamFrContainingIgnoreCase(team, team, team);
    }

    @Override
    public List<Product> filterByEra(String era) {
        return productRepository.findByEraContainingIgnoreCase(era);
    }

    @Override
    public List<Product> filterBySize(String size) {
        return productRepository.findBySize(size);
    }

    @Override
    public List<Product> filterByMaxPrice(BigDecimal price) {
        return productRepository.findByPriceLessThanEqual(price);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}