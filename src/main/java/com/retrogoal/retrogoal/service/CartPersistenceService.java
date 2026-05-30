package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.CartItem;
import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.repository.CartItemRepository;
import com.retrogoal.retrogoal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Synchronises the session cart with a persistent database cart for the logged-in user.
 */
@Service
@RequiredArgsConstructor
public class CartPersistenceService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public Optional<User> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal() == null
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }
        return userRepository.findByEmail(authentication.getName());
    }

    @Transactional(readOnly = true)
    public void loadPersistedCartIntoSession(User user, CartService cartService) {
        cartService.clear();
        cartItemRepository.findByUser(user).forEach(item -> {
            if (item.getProduct() != null && item.getQuantity() > 0) {
                cartService.addItem(item.getProduct(), item.getQuantity());
            }
        });
    }

    @Transactional(readOnly = true)
    public void loadCurrentUserCartIfSessionEmpty(CartService cartService) {
        if (!cartService.getItems().isEmpty()) {
            return;
        }
        currentUser().ifPresent(user -> loadPersistedCartIntoSession(user, cartService));
    }

    @Transactional
    public void addOrIncrement(User user, Product product, int quantity) {
        if (product == null || quantity <= 0) {
            return;
        }
        CartItem item = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(CartItem.builder()
                        .user(user)
                        .product(product)
                        .quantity(0)
                        .build());
        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    @Transactional
    public void updateQuantity(User user, Product product, int quantity) {
        if (product == null) {
            return;
        }
        if (quantity <= 0) {
            cartItemRepository.deleteByUserAndProduct(user, product);
            return;
        }
        CartItem item = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(CartItem.builder()
                        .user(user)
                        .product(product)
                        .build());
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Transactional
    public void remove(User user, Product product) {
        if (product != null) {
            cartItemRepository.deleteByUserAndProduct(user, product);
        }
    }

    @Transactional
    public void clear(User user) {
        cartItemRepository.deleteByUser(user);
    }
}
