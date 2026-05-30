package com.retrogoal.retrogoal.config;

import com.retrogoal.retrogoal.service.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Sends a login email notification and redirects to the home page.
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final EmailService emailService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            emailService.sendLoginNotification(authentication.getName());
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
