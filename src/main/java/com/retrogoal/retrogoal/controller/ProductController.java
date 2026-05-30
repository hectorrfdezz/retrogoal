package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.service.CartPersistenceService;
import com.retrogoal.retrogoal.service.ProductService;
import com.retrogoal.retrogoal.service.RecentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Handles product catalog display and product detail pages.
 */
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final RecentSearchService recentSearchService;
    private final CartPersistenceService cartPersistenceService;

    @GetMapping("/catalog")
    public String listProducts(@RequestParam(required = false) String search,
                               @RequestParam(required = false) String team,
                               @RequestParam(required = false) String era,
                               @RequestParam(required = false) String size,
                               @RequestParam(required = false) BigDecimal maxPrice,
                               @RequestParam(required = false) String league,
                               Model model) {
        List<Product> allProducts = productService.findAll();

        String searchText = normalize(search);
        String teamText = normalize(team);
        String selectedLeague = normalize(league);
        String selectedEra = normalize(era);
        String selectedSize = normalize(size);

        boolean teamFilterActive = hasText(teamText) || hasText(searchText);

        cartPersistenceService.currentUser().ifPresent(user -> {
            if (hasText(searchText)) {
                recentSearchService.registerSearch(user, searchText);
            } else if (hasText(teamText)) {
                recentSearchService.registerSearch(user, teamText);
            }
        });

        // Build the list of eras only after the user searches/selects a team. This keeps the season filter meaningful:
        // for example, if the user types "Barcelona", the dropdown shows only Barcelona seasons available in the shop.
        List<String> availableEras = teamFilterActive
                ? allProducts.stream()
                    .filter(product -> !hasText(selectedLeague) || equalsIgnoreCase(product.getLeague(), selectedLeague))
                    .filter(product -> matchesTeamOrSearch(product, teamText, searchText))
                    .map(Product::getEra)
                    .filter(Objects::nonNull)
                    .filter(value -> !value.isBlank())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList())
                : List.of();

        List<Product> products = allProducts.stream()
                // League filter works independently: LaLiga -> Barça/Real Madrid, Bundesliga -> Bayern, Ligue 1 -> PSG, etc.
                .filter(product -> !hasText(selectedLeague) || equalsIgnoreCase(product.getLeague(), selectedLeague))
                // Search by product name/team/season.
                .filter(product -> !hasText(searchText) || matchesText(product, searchText))
                // Explicit team filter.
                .filter(product -> !hasText(teamText) || matchesTeam(product, teamText))
                // Era/season is only applied when a team/search has already narrowed the catalogue.
                .filter(product -> !teamFilterActive || !hasText(selectedEra) || equalsIgnoreCase(product.getEra(), selectedEra))
                .filter(product -> !hasText(selectedSize) || equalsIgnoreCase(product.getSize(), selectedSize))
                .filter(product -> maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("leagues", allProducts.stream()
                .map(Product::getLeague)
                .filter(Objects::nonNull)
                .filter(value -> !value.isBlank())
                .distinct()
                .sorted()
                .collect(Collectors.toList()));
        model.addAttribute("teams", allProducts.stream()
                .map(Product::getTeam)
                .filter(Objects::nonNull)
                .filter(value -> !value.isBlank())
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList()));
        model.addAttribute("availableEras", availableEras);
        model.addAttribute("teamFilterActive", teamFilterActive);
        model.addAttribute("selectedSearch", searchText);
        model.addAttribute("selectedTeam", teamText);
        model.addAttribute("selectedEra", selectedEra);
        model.addAttribute("selectedSize", selectedSize);
        model.addAttribute("selectedLeague", selectedLeague);
        model.addAttribute("selectedMaxPrice", maxPrice);
        model.addAttribute("recentSearches", cartPersistenceService.currentUser()
                .map(recentSearchService::findLastThree)
                .orElse(List.of()));
        return "catalog";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/catalog?notfound";
        }
        List<Product> availableRecommendations = new ArrayList<>(productService.findAll().stream()
                .filter(candidate -> candidate.getId() != null && !candidate.getId().equals(product.getId()))
                .sorted((a, b) -> a.getId().compareTo(b.getId()))
                .collect(Collectors.toList()));
        List<Product> recommendations = new ArrayList<>();
        if (!availableRecommendations.isEmpty()) {
            int startIndex = (int) ((product.getId() == null ? 0 : product.getId() * 3) % availableRecommendations.size());
            for (int i = 0; i < Math.min(3, availableRecommendations.size()); i++) {
                recommendations.add(availableRecommendations.get((startIndex + i) % availableRecommendations.size()));
            }
        }

        model.addAttribute("product", product);
        model.addAttribute("recommendedProducts", recommendations);
        return "product";
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private boolean containsIgnoreCase(String source, String query) {
        return source != null && query != null && source.toLowerCase().contains(query.toLowerCase());
    }

    private boolean equalsIgnoreCase(String source, String query) {
        return source != null && query != null && source.equalsIgnoreCase(query);
    }

    private boolean matchesTeam(Product product, String query) {
        return containsIgnoreCase(product.getTeam(), query)
                || containsIgnoreCase(product.getTeamEn(), query)
                || containsIgnoreCase(product.getTeamFr(), query);
    }

    private boolean matchesText(Product product, String query) {
        return containsIgnoreCase(product.getName(), query)
                || containsIgnoreCase(product.getNameEn(), query)
                || containsIgnoreCase(product.getNameFr(), query)
                || containsIgnoreCase(product.getTeam(), query)
                || containsIgnoreCase(product.getTeamEn(), query)
                || containsIgnoreCase(product.getTeamFr(), query)
                || containsIgnoreCase(product.getEra(), query)
                || containsIgnoreCase(product.getLeague(), query);
    }

    private boolean matchesTeamOrSearch(Product product, String teamText, String searchText) {
        if (hasText(teamText)) {
            return matchesTeam(product, teamText);
        }
        return matchesText(product, searchText);
    }
}
