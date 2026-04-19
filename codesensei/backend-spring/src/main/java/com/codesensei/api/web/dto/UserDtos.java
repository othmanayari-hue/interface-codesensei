package com.codesensei.api.web.dto;

import com.codesensei.api.domain.SubscriptionType;
import com.codesensei.api.domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class UserDtos {
    public record UserResponse(
            long id,
            String nom,
            String prenom,
            String email,
            String role,
            LocalDate dateNaissance,
            SubscriptionType abonnement,
            int nombreConversationsRestantes
    ) {
        public static UserResponse from(User u) {
            return new UserResponse(
                    u.getId(),
                    u.getNom(),
                    u.getPrenom(),
                    u.getEmail(),
                    u.getRole().name(),
                    u.getDateNaissance(),
                    u.getAbonnement(),
                    u.getNombreConversationsRestantes()
            );
        }
    }

    public record UpdateUserRequest(
            @NotBlank @Size(max = 100) String nom,
            @NotBlank @Size(max = 100) String prenom,
            @NotNull LocalDate dateNaissance
    ) {}
}

