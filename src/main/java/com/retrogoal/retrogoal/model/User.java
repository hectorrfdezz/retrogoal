package com.retrogoal.retrogoal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents an application user (customer or administrator).
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username/email used for authentication.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * BCrypt hashed password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Display name shown in the application.
     */
    private String name;

    /**
     * First name used in the profile/settings page.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Optional last name used in the profile/settings page.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Optional phone number for customer contact.
     */
    private String phone;

    /**
     * Set of roles associated to the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    /**
     * Convenience method to convert roles into authorities for Spring Security.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}