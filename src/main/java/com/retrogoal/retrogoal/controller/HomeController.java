package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page and general pages.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Show a few products on the homepage (e.g. latest or featured)
        model.addAttribute("products", productService.findAll());
        return "index";
    }
}