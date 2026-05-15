package com.retrogoal.retrogoal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data transfer object used when registering a new user.
 */
@Data
public class UserRegistrationDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    /**
     * Confirmation password. Not persisted; used only for validation on the form.
     */
    @Size(min = 6, message = "Confirm Password must have at least 6 characters")
    private String confirmPassword;
}