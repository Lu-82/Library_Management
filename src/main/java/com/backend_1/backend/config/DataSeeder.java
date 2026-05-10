package com.backend_1.backend.config;

import com.backend_1.backend.entities.Role;
import com.backend_1.backend.entities.User;
import com.backend_1.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if an admin exists, if not, create one
        if (userRepository.findByEmail("admin@jut.edu").isEmpty()) {
            User admin = new User();

            // Add this line to satisfy the not-null constraint
            admin.setName("JUT Admin");

            admin.setEmail("admin@jut.edu");
            admin.setPassword(passwordEncoder.encode("admin123")); // Securely hash the password
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("✅ Default Admin User created: admin@jut.edu / admin123");
        }
    }
}