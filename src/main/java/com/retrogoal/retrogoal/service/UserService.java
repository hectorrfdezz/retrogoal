package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.dto.UserRegistrationDto;
import com.retrogoal.retrogoal.model.User;

public interface UserService {
    User registerUser(UserRegistrationDto registrationDto);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User updateProfile(String email, String name, String firstName, String lastName, String phone);
    void changePassword(String email, String currentPassword, String newPassword, String confirmPassword);
}
