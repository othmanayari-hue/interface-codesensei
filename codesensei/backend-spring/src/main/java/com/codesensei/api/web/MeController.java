package com.codesensei.api.web;

import com.codesensei.api.domain.User;
import com.codesensei.api.repo.UserRepository;
import com.codesensei.api.web.dto.UserDtos;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class MeController {
    private final UserRepository userRepo;

    public MeController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public UserDtos.UserResponse me(Authentication auth) {
        String email = auth.getName();
        User u = userRepo.findByEmailIgnoreCase(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserDtos.UserResponse.from(u);
    }
}

