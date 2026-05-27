package com.retrogoal.retrogoal.config;

import com.retrogoal.retrogoal.model.Product;
import com.retrogoal.retrogoal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Custom data seeder that runs after the default {@link DataInitializer}. It checks whether only the
 * placeholder products exist (three or fewer items) and, if so, clears them and seeds a richer
 * catalogue of retro and modern football shirts.  Having this logic in a separate configuration
 * allows us to enhance the sample data without modifying the original initializer directly.
 */
@Configuration
@RequiredArgsConstructor
public class CustomDataSeeder {

    private final ProductRepository productRepository;

    @Bean
    public CommandLineRunner seedAdditionalProducts() {
        return args -> {
            // Only proceed if there are three or fewer products (the default sample set)
            if (productRepository.count() <= 3) {
                productRepository.deleteAll();

                Product barca99 = Product.builder()
                        .name("FC Barcelona 1998/99")
                        .description("Recordada por el doblete de Liga y Copa del Rey con Rivaldo como estrella.")
                        .team("FC Barcelona")
                        .era("1998/99")
                        .size("M")
                        .price(new BigDecimal("34.99"))
                        .stock(15)
                        .imageUrl("/images/barca1999.png")
                        .build();
                Product madrid02 = Product.builder()
                        .name("Real Madrid 2001/02")
                        .description("La camiseta de la novena Copa de Europa con Zidane y la volea en Glasgow.")
                        .team("Real Madrid")
                        .era("2001/02")
                        .size("M")
                        .price(new BigDecimal("36.99"))
                        .stock(12)
                        .imageUrl("/images/real_madrid_2002.png")
                        .build();
                Product espana10 = Product.builder()
                        .name("Selección España 2010")
                        .description("La camiseta con la que España ganó su primer Mundial en Sudáfrica.")
                        .team("España")
                        .era("2010")
                        .size("L")
                        .price(new BigDecimal("33.99"))
                        .stock(20)
                        .imageUrl("/images/espana2010.png")
                        .build();
                Product barca2526 = Product.builder()
                        .name("FC Barcelona 2025/26")
                        .description("Diseño contemporáneo con colores azulgrana vibrantes y detalle dorado.")
                        .team("FC Barcelona")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("37.99"))
                        .stock(25)
                        .imageUrl("/images/barcelona_2526.png")
                        .build();
                Product madrid2526 = Product.builder()
                        .name("Real Madrid 2025/26")
                        .description("Clásica camiseta blanca con toques dorados celebrando su legado.")
                        .team("Real Madrid")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("37.99"))
                        .stock(25)
                        .imageUrl("/images/real_madrid_2526.png")
                        .build();
                Product city2526 = Product.builder()
                        .name("Manchester City 2025/26")
                        .description("La camiseta celeste de los campeones de la Premier con un diseño moderno.")
                        .team("Manchester City")
                        .era("2025/26")
                        .size("M")
                        .price(new BigDecimal("35.99"))
                        .stock(25)
                        .imageUrl("/images/man_city_2526.png")
                        .build();
                Product liverpool2526 = Product.builder()
                        .name("Liverpool FC 2025/26")
                        .description("El clásico rojo de Anfield con detalles blancos en cuello y mangas.")
                        .team("Liverpool")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("35.99"))
                        .stock(25)
                        .imageUrl("/images/liverpool_2526.png")
                        .build();
                Product chelsea2526 = Product.builder()
                        .name("Chelsea 2025/26")
                        .description("Azul eléctrico con ribetes dorados, un homenaje a Stamford Bridge.")
                        .team("Chelsea")
                        .era("2025/26")
                        .size("M")
                        .price(new BigDecimal("35.99"))
                        .stock(25)
                        .imageUrl("/images/chelsea_2526.png")
                        .build();
                Product bayern2526 = Product.builder()
                        .name("Bayern Munich 2025/26")
                        .description("Rojo intenso con detalles blancos, símbolo de la hegemonía bávara.")
                        .team("Bayern Munich")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("36.99"))
                        .stock(25)
                        .imageUrl("/images/bayern_2526.png")
                        .build();
                Product inter2526 = Product.builder()
                        .name("Inter de Milán 2025/26")
                        .description("Tradicional neroazzurri con líneas verticales y toques dorados.")
                        .team("Inter Milan")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("36.99"))
                        .stock(25)
                        .imageUrl("/images/inter_2526.png")
                        .build();
                Product psg2526 = Product.builder()
                        .name("Paris Saint‑Germain 2025/26")
                        .description("Azul profundo con franja roja central, fiel a su identidad parisina.")
                        .team("Paris Saint‑Germain")
                        .era("2025/26")
                        .size("L")
                        .price(new BigDecimal("36.99"))
                        .stock(25)
                        .imageUrl("/images/psg_2526.png")
                        .build();

                // Save all products first so they get IDs assigned
                java.util.List<Product> products = java.util.List.of(
                        barca99, madrid02, espana10,
                        barca2526, madrid2526, city2526,
                        liverpool2526, chelsea2526, bayern2526,
                        inter2526, psg2526
                );
                productRepository.saveAll(products);

                // Now assign simple recommendations for the "También te puede interesar" section
                barca99.setRecommendedProducts(java.util.Set.of(madrid02, espana10));
                madrid02.setRecommendedProducts(java.util.Set.of(barca99, espana10));
                espana10.setRecommendedProducts(java.util.Set.of(barca99, madrid02));
                barca2526.setRecommendedProducts(java.util.Set.of(madrid2526, city2526));
                madrid2526.setRecommendedProducts(java.util.Set.of(barca2526, bayern2526));
                city2526.setRecommendedProducts(java.util.Set.of(liverpool2526, psg2526));
                liverpool2526.setRecommendedProducts(java.util.Set.of(city2526, chelsea2526));
                chelsea2526.setRecommendedProducts(java.util.Set.of(liverpool2526, inter2526));
                bayern2526.setRecommendedProducts(java.util.Set.of(madrid2526, psg2526));
                inter2526.setRecommendedProducts(java.util.Set.of(chelsea2526, psg2526));
                psg2526.setRecommendedProducts(java.util.Set.of(city2526, barca2526));

                // Save again to persist the updated recommendation relationships
                productRepository.saveAll(products);
            }
        };
    }
}