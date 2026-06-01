package com.retrogoal.retrogoal.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles unexpected exceptions and routes them to the custom error view instead of exposing technical pages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception exception, Model model) {
        logger.error("Unhandled application error", exception);
        model.addAttribute("status", "500");
        model.addAttribute("message", "No se ha podido completar la operación. Revisa la información introducida o vuelve al dashboard.");
        return "error";
    }
}
