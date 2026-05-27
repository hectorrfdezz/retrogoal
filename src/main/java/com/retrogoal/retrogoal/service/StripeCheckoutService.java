package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Order;
import com.retrogoal.retrogoal.model.Product;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class StripeCheckoutService {

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${stripe.currency:eur}")
    private String currency;

    @Value("${stripe.secret-key:}")
    private String stripeSecretKey;

    public boolean isConfigured() {
        return stripeSecretKey != null && !stripeSecretKey.isBlank();
    }

    public Session createCheckoutSession(Order order, Map<Product, Integer> cartItems) throws StripeException {
        if (!isConfigured()) {
            throw new IllegalStateException("Stripe secret key is not configured. Set STRIPE_SECRET_KEY.");
        }

        SessionCreateParams.Builder params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(baseUrl + "/checkout/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(baseUrl + "/checkout/cancel?orderId=" + order.getId())
                .setClientReferenceId(String.valueOf(order.getId()))
                .putMetadata("orderId", String.valueOf(order.getId()));

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            if (product == null || quantity == null || quantity <= 0) {
                continue;
            }

            params.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(quantity.longValue())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(currency)
                                            .setUnitAmount(toCents(product.getPrice()))
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(product.getName())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }

        return Session.create(params.build());
    }

    public Session retrieveSession(String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }

    private Long toCents(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();
    }
}
