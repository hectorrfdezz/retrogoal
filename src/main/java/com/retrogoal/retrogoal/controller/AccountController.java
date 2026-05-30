package com.retrogoal.retrogoal.controller;

import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Allows logged-in users to review and update their account data.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    @GetMapping("/settings")
    public String settings(Authentication authentication, Model model) {
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "account/settings";
    }

    @PostMapping("/settings/profile")
    public String updateProfile(Authentication authentication,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String phone,
                                Model model) {
        User user = userService.updateProfile(authentication.getName(), name, firstName, lastName, phone);
        model.addAttribute("user", user);
        model.addAttribute("profileSuccess", "Datos actualizados correctamente.");
        return "account/settings";
    }

    @PostMapping("/settings/password")
    public String changePassword(Authentication authentication,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Model model) {
        User user = userService.findByEmail(authentication.getName());
        model.addAttribute("user", user);
        try {
            userService.changePassword(authentication.getName(), currentPassword, newPassword, confirmPassword);
            model.addAttribute("passwordSuccess", "Contraseña cambiada correctamente.");
        } catch (Exception e) {
            model.addAttribute("passwordError", e.getMessage());
        }
        return "account/settings";
    }
}
