package com.backend_1.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    // Standard Getters and Setters
    private String email;
    private String password;

}