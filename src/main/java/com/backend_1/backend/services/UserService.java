package com.backend_1.backend.services;

import com.backend_1.backend.entities.Role;
import com.backend_1.backend.entities.User;
import com.backend_1.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject the bean we created

    public User registerUser(User user) {
        // 1. Check for duplicates
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        // 2. Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // 3. Hash the password before saving!
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }
}