package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.service.CartPersistenceService;
import com.retrogoal.retrogoal.service.CartService;
import com.retrogoal.retrogoal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles shopping cart related actions.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final CartPersistenceService cartPersistenceService;

    @GetMapping
    public String viewCart(Model model) {
        cartPersistenceService.currentUser()
                .ifPresent(user -> cartPersistenceService.loadPersistedCartIntoSession(user, cartService));
        Map<Product, Integer> items = cartService.getItems();
        Map<Long, BigDecimal> lineTotals = new HashMap<>();
        BigDecimal cartTotal = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            if (product == null || product.getId() == null || product.getPrice() == null || quantity == null) {
                continue;
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity.longValue()));
            lineTotals.put(product.getId(), lineTotal);
            cartTotal = cartTotal.add(lineTotal);
        }

        BigDecimal shipping = new BigDecimal("5.00");
        model.addAttribute("cartItems", items);
        model.addAttribute("lineTotals", lineTotals);
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
            cartPersistenceService.currentUser()
                    .ifPresent(user -> cartPersistenceService.addOrIncrement(user, product, quantity));
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                 @RequestParam int quantity) {
        Product product = productService.findById(productId);
        if (product != null) {
            cartService.updateQuantity(product, quantity);
            cartPersistenceService.currentUser()
                    .ifPresent(user -> cartPersistenceService.updateQuantity(user, product, quantity));
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            cartService.removeItem(product);
            cartPersistenceService.currentUser()
                    .ifPresent(user -> cartPersistenceService.remove(user, product));
        }
        return "redirect:/cart";
    }
}