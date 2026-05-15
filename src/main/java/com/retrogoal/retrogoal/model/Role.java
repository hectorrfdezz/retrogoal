package com.retrogoal.retrogoal.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a security role within the system (e.g. ROLE_USER, ROLE_ADMIN).
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the role. By convention roles start with "ROLE_".
     */
    @Column(nullable = false, unique = true)
    private String name;
}