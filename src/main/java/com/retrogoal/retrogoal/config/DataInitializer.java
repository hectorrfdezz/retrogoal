package com.retrogoal.retrogoal.config;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.model.Role;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.repository.ProductRepository;
import com.retrogoal.retrogoal.repository.RoleRepository;
import com.retrogoal.retrogoal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Initializes the database with some sample data at startup. This includes
 * default roles, an admin account and a few products for demonstration.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Create roles if they do not exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

            // Create default admin user if not present
            if (userRepository.findByEmail("admin@retrogoal.com").isEmpty()) {
                User admin = User.builder()
                        .email("admin@retrogoal.com")
                        .password(passwordEncoder.encode("admin123"))
                        .name("Admin User")
                        .roles(Set.of(adminRole))
                        .build();
                userRepository.save(admin);
            }
            // Create a few sample products if none exist
            if (productRepository.count() == 0) {
                Product p1 = Product.builder()
                        .name("Camiseta FC Barcelona 1999")
                        .nameEn("FC Barcelona 1999 Shirt")
                        .nameFr("Maillot FC Barcelone 1999")
                        .description("Camiseta retro del FC Barcelona de la temporada 1998/99.")
                        .descriptionEn("Retro FC Barcelona shirt from the 1998/99 season.")
                        .descriptionFr("Maillot rétro du FC Barcelone de la saison 1998/99.")
                        .team("FC Barcelona")
                        .teamEn("FC Barcelona")
                        .teamFr("FC Barcelone")
                        .era("1998/99")
                        .size("M")
                        .price(new BigDecimal("59.99"))
                        .stock(10)
                        .imageUrl("/images/barca1999.png")
                        .build();
                Product p2 = Product.builder()
                        .name("Camiseta Real Madrid 2024")
                        .nameEn("Real Madrid 2024 Shirt")
                        .nameFr("Maillot Real Madrid 2024")
                        .description("Camiseta oficial del Real Madrid de la temporada 2023/24.")
                        .descriptionEn("Official Real Madrid shirt from the 2023/24 season.")
                        .descriptionFr("Maillot officiel du Real Madrid de la saison 2023/24.")
                        .team("Real Madrid")
                        .teamEn("Real Madrid")
                        .teamFr("Real Madrid")
                        .era("2023/24")
                        .size("L")
                        .price(new BigDecimal("89.99"))
                        .stock(15)
                        .imageUrl("/images/real_madrid_2024.png")
                        .build();
                Product p3 = Product.builder()
                        .name("Camiseta Selección España 2010")
                        .nameEn("Spain 2010 National Team Shirt")
                        .nameFr("Maillot de l'équipe d'Espagne 2010")
                        .description("Camiseta conmemorativa del Mundial 2010.")
                        .descriptionEn("Commemorative shirt from the 2010 World Cup.")
                        .descriptionFr("Maillot commémoratif de la Coupe du monde 2010.")
                        .team("España")
                        .teamEn("Spain")
                        .teamFr("Espagne")
                        .era("2010")
                        .size("XL")
                        .price(new BigDecimal("69.99"))
                        .stock(8)
                        .imageUrl("/images/espana2010.png")
                        .build();
                // Set simple recommendations: p1 recommends p2 and p3, etc.
                productRepository.saveAll(List.of(p1, p2, p3));
            }
        };
    }
}