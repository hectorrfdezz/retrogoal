package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.service.CartService;
import com.retrogoal.retrogoal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles shopping cart related actions.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public String viewCart(Model model) {
        // Populate cart items
        model.addAttribute("cartItems", cartService.getItems());
        // Calculate the cart subtotal and shipping to display in the cart summary
        java.math.BigDecimal cartTotal = java.math.BigDecimal.ZERO;
        for (java.util.Map.Entry<com.retrogoal.retrogoal.model.Product, Integer> entry : cartService.getItems().entrySet()) {
            // price * quantity for each item
            java.math.BigDecimal lineTotal = entry.getKey().getPrice().multiply(java.math.BigDecimal.valueOf(entry.getValue()));
            cartTotal = cartTotal.add(lineTotal);
        }
        // Flat shipping cost; adjust as necessary
        java.math.BigDecimal shipping = new java.math.BigDecimal("5.00");
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("finalTotal", cartTotal.add(shipping));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity) {
        Product product = productService.findById(productId);
        if (product != null) {
            cartService.addItem(product, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity) {
        Product product = productService.findById(productId);
        if (product != null) {
            cartService.updateQuantity(product, quantity);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            cartService.removeItem(product);
        }
        return "redirect:/cart";
    }
}