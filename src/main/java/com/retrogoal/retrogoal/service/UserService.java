package com.retrogoal.retrogoal.service;

import com.retrogoal.retrogoal.dto.UserRegistrationDto;
import com.retrogoal.retrogoal.model.User;

public interface UserService {
    /**
     * Registers a new user in the system.
     *
     * @param registrationDto Data provided by the registration form
     * @return the created user
     */
    User registerUser(UserRegistrationDto registrationDto);

    boolean existsByEmail(String email);
}