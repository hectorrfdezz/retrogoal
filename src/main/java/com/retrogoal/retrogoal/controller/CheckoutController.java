package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.service.CartService;
import com.retrogoal.retrogoal.service.OrderService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import com.retrogoal.retrogoal.model.Product;

/**
 * Handles checkout processing and order placement.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping
    public String showCheckout(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        return "checkout";
    }

    /**
     * Simple DTO for capturing checkout form inputs.
     */
    public static class CheckoutForm {
        @NotBlank
        private String shippingAddress;

        // getters and setters
        public String getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    }

    @PostMapping
    public String processCheckout(@ModelAttribute CheckoutForm form,
                                  @AuthenticationPrincipal User user,
                                  Model model) {
        Map<Product, Integer> items = cartService.getItems();
        if (items.isEmpty()) {
            model.addAttribute("error", "Your cart is empty");
            return "checkout";
        }
        // In a real application you would create a PaymentIntent with Stripe here.
        // For demonstration the payment intent ID is left null.
        var order = orderService.createOrder(user, items, form.getShippingAddress(), null);
        // Clear the cart
        cartService.clear();
        model.addAttribute("order", order);
        return "orderConfirmation";
    }
}