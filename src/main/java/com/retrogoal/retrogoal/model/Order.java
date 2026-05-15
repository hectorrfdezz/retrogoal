package com.retrogoal.retrogoal.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a customer order consisting of one or more items.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User that placed the order.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Order items included in this order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    /**
     * Status of the order.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * Date and time when the order was created.
     */
    private LocalDateTime orderDate;

    /**
     * Total price of the order.
     */
    private BigDecimal totalPrice;

    /**
     * Shipping address details. For a more complex system this could be an
     * embeddable type.
     */
    private String shippingAddress;

    /**
     * Optional Stripe payment intent ID (if paying via Stripe). This can be used
     * to reconcile payments.
     */
    private String paymentIntentId;
}