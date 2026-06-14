package com.retrogoal.retrogoal.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    //carga la clave secreta
    @Value("${stripe.secret-key:}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        if (stripeSecretKey != null && !stripeSecretKey.isBlank()) {
            //asigna la clave a la libreria de stripe
            Stripe.apiKey = stripeSecretKey;
        }
    }
}
