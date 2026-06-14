package com.retrogoal.retrogoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sirve para arrancar toda la aplicación: mvn spring-boot:run
 */
@SpringBootApplication
public class RetrogoalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrogoalApplication.class, args);
    }
}