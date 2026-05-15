package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Handles product catalog display and product detail pages.
 */
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/catalog")
    public String listProducts(@RequestParam(required = false) String search,
                               @RequestParam(required = false) String team,
                               @RequestParam(required = false) String era,
                               @RequestParam(required = false) String size,
                               @RequestParam(required = false) BigDecimal maxPrice,
                               Model model) {
        List<Product> products;
        if (search != null && !search.isEmpty()) {
            products = productService.searchByName(search);
        } else if (team != null && !team.isEmpty()) {
            products = productService.filterByTeam(team);
        } else if (era != null && !era.isEmpty()) {
            products = productService.filterByEra(era);
        } else if (size != null && !size.isEmpty()) {
            products = productService.filterBySize(size);
        } else if (maxPrice != null) {
            products = productService.filterByMaxPrice(maxPrice);
        } else {
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        return "catalog";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/catalog?notfound";
        }
        model.addAttribute("product", product);
        return "product";
    }
}