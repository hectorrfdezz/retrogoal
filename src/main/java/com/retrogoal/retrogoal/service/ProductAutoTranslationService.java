package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Product;
import org.springframework.stereotype.Service;

/**
 * Simple local auto-translation helper for admin-created products.
 *
 * This avoids forcing the administrator to manually fill English/French fields. It is intentionally
 * lightweight and does not require paid external APIs. It fills translated fields from the Spanish
 * fields so the public catalogue can keep using product.getLocalizedName(...),
 * product.getLocalizedDescription(...), product.getLocalizedTeam(...) and product.getLocalizedLeague(...).
 */
@Service
public class ProductAutoTranslationService {

    public void autoTranslate(Product product) {
        if (product == null) {
            return;
        }

        product.setNameEn(toEnglishProductName(product.getName()));
        product.setNameFr(toFrenchProductName(product.getName()));

        product.setDescriptionEn(toEnglish(product.getDescription()));
        product.setDescriptionFr(toFrench(product.getDescription()));

        product.setTeamEn(product.getTeam());
        product.setTeamFr(product.getTeam());

        product.setLeagueEn(toEnglishLeague(product.getLeague()));
        product.setLeagueFr(toFrenchLeague(product.getLeague()));
    }

    private String toEnglishProductName(String text) {
        if (isBlank(text)) {
            return text;
        }
        String trimmed = text.trim();
        if (trimmed.toLowerCase().startsWith("camiseta ")) {
            return trimmed.substring("Camiseta ".length()).trim() + " Shirt";
        }
        return toEnglish(trimmed);
    }

    private String toFrenchProductName(String text) {
        if (isBlank(text)) {
            return text;
        }
        String trimmed = text.trim();
        if (trimmed.toLowerCase().startsWith("camiseta ")) {
            return "Maillot " + trimmed.substring("Camiseta ".length()).trim();
        }
        return toFrench(trimmed);
    }

    private String toEnglish(String text) {
        if (isBlank(text)) {
            return text;
        }

        return text
                .replace("Camiseta", "Shirt")
                .replace("camiseta", "shirt")
                .replace("retro", "retro")
                .replace("Retro", "Retro")
                .replace("oficial", "official")
                .replace("Oficial", "Official")
                .replace("histórica", "historic")
                .replace("histórico", "historic")
                .replace("conmemorativa", "commemorative")
                .replace("temporada", "season")
                .replace("Temporada", "Season")
                .replace("Selección España", "Spain National Team")
                .replace("selección española", "Spanish national team")
                .replace("España", "Spain")
                .replace("de la ", "from the ")
                .replace("del ", "from the ")
                .replace(" de ", " of ");
    }

    private String toFrench(String text) {
        if (isBlank(text)) {
            return text;
        }

        return text
                .replace("Camiseta", "Maillot")
                .replace("camiseta", "maillot")
                .replace("retro", "rétro")
                .replace("Retro", "Rétro")
                .replace("oficial", "officiel")
                .replace("Oficial", "Officiel")
                .replace("histórica", "historique")
                .replace("histórico", "historique")
                .replace("conmemorativa", "commémoratif")
                .replace("temporada", "saison")
                .replace("Temporada", "Saison")
                .replace("Selección España", "Équipe d'Espagne")
                .replace("selección española", "équipe d'Espagne")
                .replace("España", "Espagne")
                .replace("del ", "du ");
    }

    private String toEnglishLeague(String league) {
        if (isBlank(league)) {
            return league;
        }
        String normalized = league.trim().toLowerCase();
        return switch (normalized) {
            case "selecciones", "selección", "seleccion" -> "National Teams";
            case "brasileirão série a", "brasileirao serie a" -> "Brazilian Serie A";
            default -> league.trim();
        };
    }

    private String toFrenchLeague(String league) {
        if (isBlank(league)) {
            return league;
        }
        String normalized = league.trim().toLowerCase();
        return switch (normalized) {
            case "selecciones", "selección", "seleccion" -> "Équipes nationales";
            case "brasileirão série a", "brasileirao serie a" -> "Championnat brésilien Série A";
            default -> league.trim();
        };
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
