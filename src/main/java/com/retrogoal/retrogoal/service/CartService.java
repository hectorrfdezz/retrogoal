package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple shopping cart stored in the user's HTTP session.
 */
@Component
@SessionScope
public class CartService {

    private final Map<Product, Integer> cartItems = new HashMap<>();

    public Map<Product, Integer> getItems() {
        return cartItems;
    }

    public void addItem(Product product, int quantity) {
        cartItems.merge(product, quantity, Integer::sum);
    }

    public void removeItem(Product product) {
        cartItems.remove(product);
    }

    public void updateQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            cartItems.remove(product);
        } else {
            cartItems.put(product, quantity);
        }
    }

    public void clear() {
        cartItems.clear();
    }
}