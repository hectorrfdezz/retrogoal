package com.retrogoal.retrogoal.service.impl;

import com.retrogoal.retrogoal.dto.UserRegistrationDto;
import com.retrogoal.retrogoal.model.Role;
import com.retrogoal.retrogoal.model.User;
import com.retrogoal.retrogoal.repository.RoleRepository;
import com.retrogoal.retrogoal.repository.UserRepository;
import com.retrogoal.retrogoal.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementation of {@link UserService} that persists users in a database.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalStateException("A user with this email already exists");
        }
        // Basic password confirmation check
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));
        User user = User.builder()
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .name(registrationDto.getName())
                .firstName(registrationDto.getName())
                .roles(Collections.singleton(userRole))
                .build();
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    @Transactional
    public User updateProfile(String email, String name, String firstName, String lastName, String phone) {
        User user = findByEmail(email);
        user.setName(name);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        User user = findByEmail(email);
        if (currentPassword == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalStateException("La contraseña actual no es correcta");
        }
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalStateException("La nueva contraseña debe tener al menos 6 caracteres");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalStateException("Las nuevas contraseñas no coinciden");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}