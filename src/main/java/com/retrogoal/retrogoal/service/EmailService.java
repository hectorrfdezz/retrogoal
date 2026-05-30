package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Sends optional email notifications. It is disabled by default and becomes active when APP_MAIL_ENABLED=true
 * and SMTP_* variables are configured.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${app.mail.enabled:false}")
    private boolean enabled;

    @Value("${app.mail.from:no-reply@retrogoal.local}")
    private String from;

    public boolean isEnabled() {
        return enabled && mailSenderProvider.getIfAvailable() != null && from != null && !from.isBlank();
    }

    public void sendLoginNotification(String to) {
        if (!isEnabled() || to == null || to.isBlank()) {
            return;
        }
        String body = "Hola,\n\nSe ha iniciado sesión en tu cuenta de RetroGoal el " + LocalDateTime.now()
                + ".\n\nSi has sido tú, puedes ignorar este correo. Si no reconoces este acceso, cambia tu contraseña cuanto antes.\n\nRetroGoal";
        send(to, "Nuevo inicio de sesión en RetroGoal", body);
    }

    public void sendPaymentConfirmation(String to, Order order) {
        if (!isEnabled() || to == null || to.isBlank() || order == null) {
            return;
        }
        String body = "Hola,\n\nTu pago se ha confirmado correctamente.\n\n"
                + "Pedido: #" + order.getId() + "\n"
                + "Estado: " + order.getStatus() + "\n"
                + "Total: " + order.getTotalPrice() + " €\n\n"
                + "Gracias por comprar en RetroGoal.";
        send(to, "Pago confirmado - Pedido #" + order.getId(), body);
    }

    private void send(String to, String subject, String body) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception ignored) {
            // Email must never break login or checkout in this educational project.
        }
    }
}
