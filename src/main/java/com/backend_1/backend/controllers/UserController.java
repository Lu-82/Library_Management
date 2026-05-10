package com.backend_1.backend.controllers;


import com.backend_1.backend.dto.LoginRequest;
import com.backend_1.backend.entities.User;
import com.backend_1.backend.security.JwtService;
import com.backend_1.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // 1. Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. If authentication is successful
        if (authentication.isAuthenticated()) {
            // Retrieve the User object from the authentication result
            // Note: This works because your User entity implements UserDetails
            User user = (User) authentication.getPrincipal();

            // 3. Generate token using the full User object (so role is included)
            return jwtService.generateToken(user);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @org.springframework.web.bind.annotation.GetMapping
    public java.util.List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}