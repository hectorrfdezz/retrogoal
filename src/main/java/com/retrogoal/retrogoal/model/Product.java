package com.retrogoal.retrogoal.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Represents a football shirt product available for purchase.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2048)
    private String description;

    /**
     * Football team associated to the shirt (e.g. FC Barcelona).
     */
    private String team;

    /**
     * Era or season of the shirt (e.g. 1998/99, Modern).
     */
    private String era;

    /**
     * Size label (e.g. S, M, L, XL). Could be extended to enumerated type.
     */
    private String size;

    /**
     * Unit price of the product.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Available stock quantity.
     */
    private int stock;

    /**
     * URL or path to the product image.
     */
    private String imageUrl;

    /**
     * Recommended products that are related to this product. Many‑to‑many
     * relationship with the same table to allow simple recommendations.
     */
    @ManyToMany
    @JoinTable(name = "product_recommendations",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "recommended_product_id"))
    private Set<Product> recommendedProducts;
}