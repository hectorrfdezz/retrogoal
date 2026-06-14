package com.retrogoal.retrogoal.config;

import com.retrogoal.retrogoal.service.CartPersistenceService;
import com.retrogoal.retrogoal.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Añade datos comunes a todas las vistas Thymeleaf.
 */
@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final CartService cartService;
    private final CartPersistenceService cartPersistenceService;

    /**
     * Permite mostrar el email del usuario logueado
     */
    @ModelAttribute("currentUserEmail")
    public String currentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() != null
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Permite mostrar el número de productos del carrito
     */
    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        cartPersistenceService.loadCurrentUserCartIfSessionEmpty(cartService);
        return cartService.getItems().values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Permite saber si el usuario actual es admin para mostrar enlaces del panel
     */
    @ModelAttribute("currentUserIsAdmin")
    public boolean currentUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal() == null
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

}