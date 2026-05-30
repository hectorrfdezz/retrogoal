package com.retrogoal.retrogoal.repository;

import com.retrogoal.retrogoal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCaseOrNameEnContainingIgnoreCaseOrNameFrContainingIgnoreCase(String name, String nameEn, String nameFr);
    List<Product> findByTeamContainingIgnoreCaseOrTeamEnContainingIgnoreCaseOrTeamFrContainingIgnoreCase(String team, String teamEn, String teamFr);
    List<Product> findByEraContainingIgnoreCase(String era);
    List<Product> findBySize(String size);
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    /**
     * Finds products by league case-insensitively.
     *
     * @param league the league name to search for
     * @return list of matching products
     */
    List<Product> findByLeagueIgnoreCase(String league);

    /**
     * Finds products by retro flag.
     *
     * @param retro true to find retro products, false for current season
     * @return list of matching products
     */
    List<Product> findByRetro(boolean retro);
}