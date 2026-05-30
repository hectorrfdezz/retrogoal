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
 * Replaces the initial placeholder products with the main RetroGoal catalogue.
 */
@Configuration
@RequiredArgsConstructor
public class CustomDataSeeder {

    private final ProductRepository productRepository;

    @Bean
    public CommandLineRunner seedAdditionalProducts() {
        return args -> {
            if (productRepository.count() <= 3) {
                productRepository.deleteAll();

                Product barca99 = product(
                        "FC Barcelona 1998/99",
                        "Remembered for the league and cup double with Rivaldo as the star player.",
                        "Souvenir du doublé Liga et Coupe du Roi avec Rivaldo comme grande star.",
                        "Recordada por el doblete de Liga y Copa del Rey con Rivaldo como estrella.",
                        "FC Barcelona", "FC Barcelona", "FC Barcelone", "1998/99", "M",
                        "34.99", 15, "/images/barca1999.png");

                Product madrid02 = product(
                        "Real Madrid 2001/02",
                        "The shirt of the ninth European Cup, remembered for Zidane's volley in Glasgow.",
                        "Le maillot de la neuvième Coupe d'Europe, marqué par la volée de Zidane à Glasgow.",
                        "La camiseta de la novena Copa de Europa con Zidane y la volea en Glasgow.",
                        "Real Madrid", "Real Madrid", "Real Madrid", "2001/02", "M",
                        "36.99", 12, "/images/real_madrid_2002.png");

                Product espana10 = product(
                        "Selección España 2010",
                        "The shirt worn when Spain won its first World Cup in South Africa.",
                        "Le maillot porté lorsque l'Espagne a remporté sa première Coupe du monde en Afrique du Sud.",
                        "La camiseta con la que España ganó su primer Mundial en Sudáfrica.",
                        "España", "Spain", "Espagne", "2010", "L",
                        "33.99", 20, "/images/espana2010.png");
                espana10.setNameEn("Spain 2010 National Team Shirt");
                espana10.setNameFr("Maillot de l'équipe d'Espagne 2010");

                Product barca2526 = product(
                        "FC Barcelona 2025/26",
                        "Contemporary design with vibrant blaugrana colours and golden details.",
                        "Design contemporain avec des couleurs blaugrana vibrantes et des détails dorés.",
                        "Diseño contemporáneo con colores azulgrana vibrantes y detalle dorado.",
                        "FC Barcelona", "FC Barcelona", "FC Barcelone", "2025/26", "L",
                        "37.99", 25, "/images/barcelona_2526.png");

                Product madrid2526 = product(
                        "Real Madrid 2025/26",
                        "Classic white shirt with golden touches celebrating the club's legacy.",
                        "Maillot blanc classique avec des touches dorées célébrant l'héritage du club.",
                        "Clásica camiseta blanca con toques dorados celebrando su legado.",
                        "Real Madrid", "Real Madrid", "Real Madrid", "2025/26", "L",
                        "37.99", 25, "/images/real_madrid_2526.png");

                Product city2526 = product(
                        "Manchester City 2025/26",
                        "The sky-blue shirt of the Premier League champions with a modern design.",
                        "Le maillot bleu ciel des champions de Premier League avec un design moderne.",
                        "La camiseta celeste de los campeones de la Premier con un diseño moderno.",
                        "Manchester City", "Manchester City", "Manchester City", "2025/26", "M",
                        "35.99", 25, "/images/man_city_2526.png");

                Product liverpool2526 = product(
                        "Liverpool FC 2025/26",
                        "Classic Anfield red with white details on the collar and sleeves.",
                        "Le rouge classique d'Anfield avec des détails blancs au col et aux manches.",
                        "El clásico rojo de Anfield con detalles blancos en cuello y mangas.",
                        "Liverpool", "Liverpool", "Liverpool", "2025/26", "L",
                        "35.99", 25, "/images/liverpool_2526.png");

                Product chelsea2526 = product(
                        "Chelsea 2025/26",
                        "Electric blue with golden trims, a tribute to Stamford Bridge.",
                        "Bleu électrique avec des bordures dorées, un hommage à Stamford Bridge.",
                        "Azul eléctrico con ribetes dorados, un homenaje a Stamford Bridge.",
                        "Chelsea", "Chelsea", "Chelsea", "2025/26", "M",
                        "35.99", 25, "/images/chelsea_2526.png");

                Product bayern2526 = product(
                        "Bayern Munich 2025/26",
                        "Intense red with white details, a symbol of Bavarian dominance.",
                        "Rouge intense avec des détails blancs, symbole de la domination bavaroise.",
                        "Rojo intenso con detalles blancos, símbolo de la hegemonía bávara.",
                        "Bayern Munich", "Bayern Munich", "Bayern Munich", "2025/26", "L",
                        "36.99", 25, "/images/bayern_2526.png");

                Product inter2526 = product(
                        "Inter de Milán 2025/26",
                        "Traditional nerazzurri shirt with vertical stripes and golden touches.",
                        "Maillot nerazzurri traditionnel avec des rayures verticales et des touches dorées.",
                        "Tradicional neroazzurri con líneas verticales y toques dorados.",
                        "Inter Milan", "Inter Milan", "Inter Milan", "2025/26", "L",
                        "36.99", 25, "/images/inter_2526.png");
                inter2526.setNameEn("Inter Milan 2025/26");
                inter2526.setNameFr("Inter Milan 2025/26");

                Product psg2526 = product(
                        "Paris Saint-Germain 2025/26",
                        "Deep blue with a central red stripe, faithful to its Parisian identity.",
                        "Bleu profond avec une bande rouge centrale, fidèle à son identité parisienne.",
                        "Azul profundo con franja roja central, fiel a su identidad parisina.",
                        "Paris Saint-Germain", "Paris Saint-Germain", "Paris Saint-Germain", "2025/26", "L",
                        "36.99", 25, "/images/psg_2526.png");

                List<Product> products = List.of(
                        barca99, madrid02, espana10,
                        barca2526, madrid2526, city2526,
                        liverpool2526, chelsea2526, bayern2526,
                        inter2526, psg2526
                );
                productRepository.saveAll(products);

                barca99.setRecommendedProducts(Set.of(madrid02, espana10));
                madrid02.setRecommendedProducts(Set.of(barca99, espana10));
                espana10.setRecommendedProducts(Set.of(barca99, madrid02));
                barca2526.setRecommendedProducts(Set.of(madrid2526, city2526));
                madrid2526.setRecommendedProducts(Set.of(barca2526, bayern2526));
                city2526.setRecommendedProducts(Set.of(liverpool2526, psg2526));
                liverpool2526.setRecommendedProducts(Set.of(city2526, chelsea2526));
                chelsea2526.setRecommendedProducts(Set.of(liverpool2526, inter2526));
                bayern2526.setRecommendedProducts(Set.of(madrid2526, psg2526));
                inter2526.setRecommendedProducts(Set.of(chelsea2526, psg2526));
                psg2526.setRecommendedProducts(Set.of(city2526, barca2526));

                productRepository.saveAll(products);
            }
        };
    }

    private Product product(String name,
                            String descriptionEn,
                            String descriptionFr,
                            String description,
                            String team,
                            String teamEn,
                            String teamFr,
                            String era,
                            String size,
                            String price,
                            int stock,
                            String imageUrl) {
        return Product.builder()
                .name(name)
                .nameEn(name)
                .nameFr(name)
                .description(description)
                .descriptionEn(descriptionEn)
                .descriptionFr(descriptionFr)
                .team(team)
                .teamEn(teamEn)
                .teamFr(teamFr)
                .era(era)
                .size(size)
                .price(new BigDecimal(price))
                .stock(stock)
                .imageUrl(imageUrl)
                .build();
    }
}
