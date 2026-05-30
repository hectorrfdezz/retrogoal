package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.OrderStatus;
import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.service.CartPersistenceService;
import com.retrogoal.retrogoal.service.CartService;
import com.retrogoal.retrogoal.service.OrderService;
import com.retrogoal.retrogoal.service.StripeCheckoutService;
import com.stripe.model.checkout.Session;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Handles checkout processing and Stripe Checkout payment redirection.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;
    private final CartPersistenceService cartPersistenceService;
    private final OrderService orderService;
    private final StripeCheckoutService stripeCheckoutService;

    @GetMapping
    public String showCheckout(Model model) {
        cartPersistenceService.currentUser()
                .ifPresent(user -> cartPersistenceService.loadPersistedCartIntoSession(user, cartService));
        addCartModelAttributes(model, cartService.getItems());
        return "checkout";
    }

    /**
     * Simple DTO for capturing checkout form inputs.
     */
    public static class CheckoutForm {
        @NotBlank
        private String shippingAddress;

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }
    }

    @PostMapping
    public String processCheckout(@ModelAttribute CheckoutForm form,
                                  @AuthenticationPrincipal User user,
                                  Locale locale,
                                  Model model) {
        Map<Product, Integer> items = cartService.getItems();

        if (items.isEmpty()) {
            model.addAttribute("error", "El carrito está vacío.");
            addCartModelAttributes(model, items);
            return "checkout";
        }

        try {
            Order order = orderService.createOrder(user, items, form.getShippingAddress());
            Session session = stripeCheckoutService.createCheckoutSession(order, items, locale.getLanguage());
            return "redirect:" + session.getUrl();
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo iniciar el pago con Stripe. Revisa las variables STRIPE_SECRET_KEY y APP_BASE_URL.");
            addCartModelAttributes(model, items);
            return "checkout";
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId, Model model) {
        try {
            Session session = stripeCheckoutService.retrieveSession(sessionId);
            String orderIdValue = session.getMetadata() != null ? session.getMetadata().get("orderId") : null;

            if (orderIdValue == null || orderIdValue.isBlank()) {
                model.addAttribute("error", "No se ha podido localizar el pedido asociado al pago.");
                addCartModelAttributes(model, cartService.getItems());
                return "checkout";
            }

            Long orderId = Long.valueOf(orderIdValue);
            Order order = orderService.findById(orderId);

            if (order == null) {
                model.addAttribute("error", "No se ha encontrado el pedido.");
                return "checkout";
            }

            if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {
                order = orderService.updateStatus(orderId, OrderStatus.PAID);
                cartPersistenceService.currentUser().ifPresent(cartPersistenceService::clear);
                cartService.clear();
            }

            model.addAttribute("order", order);
            model.addAttribute("paymentStatus", session.getPaymentStatus());
            return "orderConfirmation";
        } catch (Exception e) {
            model.addAttribute("error", "El pago se ha completado, pero no se pudo confirmar automáticamente el pedido.");
            addCartModelAttributes(model, cartService.getItems());
            return "checkout";
        }
    }

    @GetMapping("/cancel")
    public String paymentCancel(@RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.findById(orderId);

        if (order != null && order.getStatus() == OrderStatus.PENDING) {
            orderService.updateStatus(orderId, OrderStatus.CANCELLED);
        }

        model.addAttribute("error", "Pago cancelado. Puedes revisar el carrito e intentarlo de nuevo.");
        addCartModelAttributes(model, cartService.getItems());
        return "checkout";
    }

    private void addCartModelAttributes(Model model, Map<Product, Integer> items) {
        Map<Long, BigDecimal> lineTotals = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            if (product == null || product.getId() == null || product.getPrice() == null || quantity == null) {
                continue;
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity.longValue()));
            lineTotals.put(product.getId(), lineTotal);
        }

        model.addAttribute("cartItems", items);
        model.addAttribute("lineTotals", lineTotals);
    }
}
