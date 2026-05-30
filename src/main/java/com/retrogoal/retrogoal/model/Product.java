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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2048)
    private String description;

    private String team;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "description_en", length = 2048)
    private String descriptionEn;

    @Column(name = "team_en")
    private String teamEn;

    @Column(name = "name_fr")
    private String nameFr;

    @Column(name = "description_fr", length = 2048)
    private String descriptionFr;

    @Column(name = "team_fr")
    private String teamFr;

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
     * League to which the team belongs (e.g. LaLiga, Premier League, Bundesliga, Ligue 1).
     * Stored in default language but can be localized via separate fields.
     */
    private String league;

    /**
     * League name in English.
     */
    @Column(name = "league_en")
    private String leagueEn;

    /**
     * League name in French.
     */
    @Column(name = "league_fr")
    private String leagueFr;

    /**
     * Indicates whether the product is a retro (classic) shirt or a current season shirt.
     */
    @Builder.Default
    private boolean retro = false;

    /**
     * Recommended products that are related to this product. Many-to-many
     * relationship with the same table to allow simple recommendations.
     */
    @ManyToMany
    @JoinTable(name = "product_recommendations",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "recommended_product_id"))
    private Set<Product> recommendedProducts;

    public String getLocalizedName(String language) {
        if ("en".equalsIgnoreCase(language)) {
            return fallback(nameEn, name);
        }
        if ("fr".equalsIgnoreCase(language)) {
            return fallback(nameFr, name);
        }
        return name;
    }

    /**
     * Returns the localized league name for the given language. Falls back to the default league when translation is unavailable.
     *
     * @param language the ISO language code (es, en, fr)
     * @return translated league name or default league
     */
    public String getLocalizedLeague(String language) {
        if (league == null) {
            return null;
        }
        if ("en".equalsIgnoreCase(language)) {
            return fallback(leagueEn, league);
        }
        if ("fr".equalsIgnoreCase(language)) {
            return fallback(leagueFr, league);
        }
        return league;
    }

    public String getLocalizedDescription(String language) {
        if ("en".equalsIgnoreCase(language)) {
            return fallback(descriptionEn, description);
        }
        if ("fr".equalsIgnoreCase(language)) {
            return fallback(descriptionFr, description);
        }
        return description;
    }

    public String getLocalizedTeam(String language) {
        if ("en".equalsIgnoreCase(language)) {
            return fallback(teamEn, team);
        }
        if ("fr".equalsIgnoreCase(language)) {
            return fallback(teamFr, team);
        }
        return team;
    }

    private String fallback(String translatedValue, String defaultValue) {
        return translatedValue == null || translatedValue.isBlank() ? defaultValue : translatedValue;
    }
}
