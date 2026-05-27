package com.retrogoal.retrogoal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for displaying a simple map page using Leaflet and OpenStreetMap.
 */
@Controller
public class MapController {
    /**
     * Displays the map page.
     *
     * @return the name of the Thymeleaf template for the map
     */
    @GetMapping("/map")
    public String showMap() {
        return "map";
    }
}