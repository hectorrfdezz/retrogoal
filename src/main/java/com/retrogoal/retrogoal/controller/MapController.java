package com.retrogoal.retrogoal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @Value("${google.maps.api-key:}")
    private String googleMapsApiKey;

    @Value("${store.location.name:RetroGoal Sevilla}")
    private String storeName;

    @Value("${store.location.address:Calle Sierpes 1, 41004 Sevilla, España}")
    private String storeAddress;

    @Value("${store.location.latitude:37.3891}")
    private double storeLatitude;

    @Value("${store.location.longitude:-5.9845}")
    private double storeLongitude;

    @GetMapping("/map")
    public String showMap(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        model.addAttribute("storeName", storeName);
        model.addAttribute("storeAddress", storeAddress);
        model.addAttribute("storeLatitude", storeLatitude);
        model.addAttribute("storeLongitude", storeLongitude);
        model.addAttribute("hasGoogleMapsApiKey", googleMapsApiKey != null && !googleMapsApiKey.isBlank());
        return "map";
    }
}
