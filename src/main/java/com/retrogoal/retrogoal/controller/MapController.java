package com.retrogoal.retrogoal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class MapController {

    private final Environment environment;

    @Value("${google.maps.api-key:}")
    private String googleMapsApiKey;

    @Value("${store.location.latitude:37.3891}")
    private double storeLatitude;

    @Value("${store.location.longitude:-5.9845}")
    private double storeLongitude;

    public MapController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/map")
    public String showMap(Model model, Locale locale) {
        String language = normalizeLanguage(locale);
        String storeName = getLocalizedProperty("store.location.name", language, "RetroGoal Sevilla");
        String storeAddress = getLocalizedProperty("store.location.address", language, "Calle Sierpes 1, 41004 Sevilla, España");

        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        model.addAttribute("storeName", storeName);
        model.addAttribute("storeAddress", storeAddress);
        model.addAttribute("storeLatitude", storeLatitude);
        model.addAttribute("storeLongitude", storeLongitude);
        model.addAttribute("mapLanguage", language);
        model.addAttribute("hasGoogleMapsApiKey", googleMapsApiKey != null && !googleMapsApiKey.isBlank());
        return "map";
    }

    private String normalizeLanguage(Locale locale) {
        if (locale == null || locale.getLanguage() == null) {
            return "es";
        }
        String language = locale.getLanguage().toLowerCase(Locale.ROOT);
        return switch (language) {
            case "en", "fr" -> language;
            default -> "es";
        };
    }

    private String getLocalizedProperty(String baseKey, String language, String defaultValue) {
        String localizedValue = environment.getProperty(baseKey + "." + language);
        if (localizedValue != null && !localizedValue.isBlank()) {
            return localizedValue;
        }
        String fallbackValue = environment.getProperty(baseKey);
        return fallbackValue == null || fallbackValue.isBlank() ? defaultValue : fallbackValue;
    }
}
