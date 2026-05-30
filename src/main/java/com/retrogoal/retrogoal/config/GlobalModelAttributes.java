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
 * Adds common attributes to all Thymeleaf models.  This includes the current user's email
 * (if authenticated) and a count of items in the shopping cart.  Using a ControllerAdvice
 * avoids duplicating this logic across controllers and allows the navigation bar to display
 * user and cart information consistently.
 */
@Component
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttributes {

    private final CartService cartService;
    private final CartPersistenceService cartPersistenceService;

    /**
     * Returns the email of the currently authenticated user or null if no user is logged in.
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
     * Calculates the total number of items in the shopping cart.
     */
    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        cartPersistenceService.loadCurrentUserCartIfSessionEmpty(cartService);
        return cartService.getItems().values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Indicates whether the current user has ROLE_ADMIN. Used by the navbar to show admin links.
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