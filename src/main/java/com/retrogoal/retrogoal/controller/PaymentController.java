package com.retrogoal.retrogoal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Keeps the old /payment URL compatible and sends users to the real Stripe checkout flow.
 */
@Controller
public class PaymentController {

    @GetMapping("/payment")
    public String redirectToCheckout() {
        return "redirect:/checkout";
    }
}
