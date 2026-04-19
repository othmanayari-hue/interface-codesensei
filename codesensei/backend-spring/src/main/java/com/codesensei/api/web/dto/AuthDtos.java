package com.codesensei.api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AuthDtos {
    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    public record RegisterRequest(
            @NotBlank @Size(max = 100) String nom,
            @NotBlank @Size(max = 100) String prenom,
            @NotBlank @Email @Size(max = 190) String email,
            @NotNull LocalDate dateNaissance,
            @NotBlank @Size(min = 6, max = 100) String password
    ) {}

    public record AuthResponse(
            String token,
            long userId,
            String email,
            String role
    ) {}
}

