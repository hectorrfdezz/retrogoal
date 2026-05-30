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
                // FC Barcelona 1999 (Retro)
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
                        .league("LaLiga")
                        .leagueEn("LaLiga")
                        .leagueFr("LaLiga")
                        .retro(true)
                        .build();

                // Real Madrid 2024 (Current)
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
                        .league("LaLiga")
                        .leagueEn("LaLiga")
                        .leagueFr("LaLiga")
                        .retro(false)
                        .build();

                // Spain National Team 2010 (Retro)
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
                        .league("Selecciones")
                        .leagueEn("National Teams")
                        .leagueFr("Sélections")
                        .retro(true)
                        .build();

                // Manchester United 1999 (Retro)
                Product p4 = Product.builder()
                        .name("Camiseta Manchester United 1999")
                        .nameEn("Manchester United 1999 Shirt")
                        .nameFr("Maillot Manchester United 1999")
                        .description("Camiseta histórica del Manchester United de la temporada 1998/99.")
                        .descriptionEn("Historic Manchester United shirt from the 1998/99 season.")
                        .descriptionFr("Maillot historique de Manchester United de la saison 1998/99.")
                        .team("Manchester United")
                        .teamEn("Manchester United")
                        .teamFr("Manchester United")
                        .era("1998/99")
                        .size("L")
                        .price(new BigDecimal("64.99"))
                        .stock(12)
                        .imageUrl("/images/liverpool_2526.png") // placeholder to be replaced
                        .league("Premier League")
                        .leagueEn("Premier League")
                        .leagueFr("Premier League")
                        .retro(true)
                        .build();

                // Bayern Munich 2013 (Retro)
                Product p5 = Product.builder()
                        .name("Camiseta Bayern de Múnich 2013")
                        .nameEn("Bayern Munich 2013 Shirt")
                        .nameFr("Maillot Bayern Munich 2013")
                        .description("Camiseta conmemorativa del triplete de 2013.")
                        .descriptionEn("Commemorative shirt of the 2013 treble season.")
                        .descriptionFr("Maillot commémoratif de la saison du triplé 2013.")
                        .team("Bayern Munich")
                        .teamEn("Bayern Munich")
                        .teamFr("Bayern Munich")
                        .era("2012/13")
                        .size("M")
                        .price(new BigDecimal("74.99"))
                        .stock(9)
                        .imageUrl("/images/bayern_2526.png") // placeholder to be replaced
                        .league("Bundesliga")
                        .leagueEn("Bundesliga")
                        .leagueFr("Bundesliga")
                        .retro(true)
                        .build();

                // Paris Saint-Germain 2025/26 (Modern)
                Product p6 = Product.builder()
                        .name("Camiseta PSG 2025/26")
                        .nameEn("PSG 2025/26 Shirt")
                        .nameFr("Maillot PSG 2025/26")
                        .description("Camiseta oficial del Paris Saint-Germain para la temporada 2025/26.")
                        .descriptionEn("Official Paris Saint-Germain shirt for the 2025/26 season.")
                        .descriptionFr("Maillot officiel du Paris Saint-Germain pour la saison 2025/26.")
                        .team("Paris Saint-Germain")
                        .teamEn("Paris Saint-Germain")
                        .teamFr("Paris Saint-Germain")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("94.99"))
                        .stock(20)
                        .imageUrl("/images/psg_2526.png")
                        .league("Ligue 1")
                        .leagueEn("Ligue 1")
                        .leagueFr("Ligue 1")
                        .retro(false)
                        .build();

                // Chelsea 2025/26 (Modern)
                Product p7 = Product.builder()
                        .name("Camiseta Chelsea 2025/26")
                        .nameEn("Chelsea 2025/26 Shirt")
                        .nameFr("Maillot Chelsea 2025/26")
                        .description("Camiseta oficial del Chelsea para la temporada 2025/26.")
                        .descriptionEn("Official Chelsea shirt for the 2025/26 season.")
                        .descriptionFr("Maillot officiel de Chelsea pour la saison 2025/26.")
                        .team("Chelsea")
                        .teamEn("Chelsea")
                        .teamFr("Chelsea")
                        .era("2025/26")
                        .size("M")
                        .price(new BigDecimal("89.99"))
                        .stock(14)
                        .imageUrl("/images/chelsea_2526.png")
                        .league("Premier League")
                        .leagueEn("Premier League")
                        .leagueFr("Premier League")
                        .retro(false)
                        .build();

                // Inter Milan 2025/26 (Modern)
                Product p8 = Product.builder()
                        .name("Camiseta Inter de Milán 2025/26")
                        .nameEn("Inter Milan 2025/26 Shirt")
                        .nameFr("Maillot Inter Milan 2025/26")
                        .description("Camiseta oficial del Inter de Milán para la temporada 2025/26.")
                        .descriptionEn("Official Inter Milan shirt for the 2025/26 season.")
                        .descriptionFr("Maillot officiel de l'Inter Milan pour la saison 2025/26.")
                        .team("Inter Milan")
                        .teamEn("Inter Milan")
                        .teamFr("Inter Milan")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("89.99"))
                        .stock(13)
                        .imageUrl("/images/inter_2526.png")
                        .league("Serie A")
                        .leagueEn("Serie A")
                        .leagueFr("Serie A")
                        .retro(false)
                        .build();

                // Manchester City 2025/26 (Modern)
                Product p9 = Product.builder()
                        .name("Camiseta Manchester City 2025/26")
                        .nameEn("Manchester City 2025/26 Shirt")
                        .nameFr("Maillot Manchester City 2025/26")
                        .description("Camiseta oficial del Manchester City para la temporada 2025/26.")
                        .descriptionEn("Official Manchester City shirt for the 2025/26 season.")
                        .descriptionFr("Maillot officiel de Manchester City pour la saison 2025/26.")
                        .team("Manchester City")
                        .teamEn("Manchester City")
                        .teamFr("Manchester City")
                        .era("2025/26")
                        .size("M")
                        .price(new BigDecimal("94.99"))
                        .stock(18)
                        .imageUrl("/images/man_city_2526.png")
                        .league("Premier League")
                        .leagueEn("Premier League")
                        .leagueFr("Premier League")
                        .retro(false)
                        .build();

                // Bayern Munich 2025/26 (Modern)
                Product p10 = Product.builder()
                        .name("Camiseta Bayern de Múnich 2025/26")
                        .nameEn("Bayern Munich 2025/26 Shirt")
                        .nameFr("Maillot Bayern Munich 2025/26")
                        .description("Camiseta oficial del Bayern de Múnich para la temporada 2025/26.")
                        .descriptionEn("Official Bayern Munich shirt for the 2025/26 season.")
                        .descriptionFr("Maillot officiel du Bayern Munich pour la saison 2025/26.")
                        .team("Bayern Munich")
                        .teamEn("Bayern Munich")
                        .teamFr("Bayern Munich")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("94.99"))
                        .stock(15)
                        .imageUrl("/images/bayern_2526.png")
                        .league("Bundesliga")
                        .leagueEn("Bundesliga")
                        .leagueFr("Bundesliga")
                        .retro(false)
                        .build();

                // Real Madrid 2002 (Retro)
                Product p11 = Product.builder()
                        .name("Camiseta Real Madrid 2002")
                        .nameEn("Real Madrid 2002 Shirt")
                        .nameFr("Maillot Real Madrid 2002")
                        .description("Camiseta conmemorativa de la novena Copa de Europa en 2002.")
                        .descriptionEn("Commemorative shirt of the ninth European Cup in 2002.")
                        .descriptionFr("Maillot commémoratif de la neuvième Coupe d'Europe en 2002.")
                        .team("Real Madrid")
                        .teamEn("Real Madrid")
                        .teamFr("Real Madrid")
                        .era("2001/02")
                        .size("M")
                        .price(new BigDecimal("79.99"))
                        .stock(7)
                        .imageUrl("/images/real_madrid_2002.png")
                        .league("LaLiga")
                        .leagueEn("LaLiga")
                        .leagueFr("LaLiga")
                        .retro(true)
                        .build();

                // FC Barcelona 2025/26 (Current)
                Product p12 = Product.builder()
                        .name("Camiseta FC Barcelona 2025/26")
                        .nameEn("FC Barcelona 2025/26 Shirt")
                        .nameFr("Maillot FC Barcelone 2025/26")
                        .description("Camiseta actual del FC Barcelona para la temporada 2025/26.")
                        .descriptionEn("Current FC Barcelona shirt for the 2025/26 season.")
                        .descriptionFr("Maillot actuel du FC Barcelone pour la saison 2025/26.")
                        .team("FC Barcelona")
                        .teamEn("FC Barcelona")
                        .teamFr("FC Barcelone")
                        .era("2025/26")
                        .size("M")
                        .price(new BigDecimal("94.99"))
                        .stock(17)
                        .imageUrl("/images/barcelona_2526.png")
                        .league("LaLiga")
                        .leagueEn("LaLiga")
                        .leagueFr("LaLiga")
                        .retro(false)
                        .build();

                // Save all products to repository
                productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
            }
        };
    }
}