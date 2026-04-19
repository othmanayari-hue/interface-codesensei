package com.codesensei.api.web;

import com.codesensei.api.domain.User;
import com.codesensei.api.security.JwtService;
import com.codesensei.api.security.UserPrincipal;
import com.codesensei.api.service.UserService;
import com.codesensei.api.web.dto.AuthDtos;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authManager, JwtService jwtService) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public AuthDtos.AuthResponse register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
        User u = userService.register(req.nom(), req.prenom(), req.email(), req.dateNaissance(), req.password());
        String token = jwtService.createToken(u.getId(), u.getEmail(), u.getRole());
        return new AuthDtos.AuthResponse(token, u.getId(), u.getEmail(), u.getRole().name());
    }

    @PostMapping("/login")
    public AuthDtos.AuthResponse login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        User u = userService.findByEmail(principal.getUsername()).orElseThrow();
        String token = jwtService.createToken(u.getId(), u.getEmail(), u.getRole());
        return new AuthDtos.AuthResponse(token, u.getId(), u.getEmail(), u.getRole().name());
    }
}

