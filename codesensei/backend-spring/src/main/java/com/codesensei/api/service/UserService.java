package com.codesensei.api.service;

import com.codesensei.api.domain.SubscriptionType;
import com.codesensei.api.domain.User;
import com.codesensei.api.domain.UserRole;
import com.codesensei.api.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final SubscriptionRules rules;

    public UserService(UserRepository userRepo, PasswordEncoder encoder, SubscriptionRules rules) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.rules = rules;
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmailIgnoreCase(email);
    }

    public User requireById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public User register(String nom, String prenom, String email, LocalDate dateNaissance, String rawPassword) {
        if (userRepo.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email already used");
        }
        User u = new User();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setEmail(email.trim().toLowerCase());
        u.setDateNaissance(dateNaissance);
        u.setRole(UserRole.USER);
        u.setAbonnement(SubscriptionType.NONE);
        u.setNombreConversationsRestantes(rules.creditsFor(SubscriptionType.NONE));
        u.setPassword(encoder.encode(rawPassword));
        return userRepo.save(u);
    }

    @Transactional
    public void decrementConversation(User user) {
        int left = user.getNombreConversationsRestantes();
        if (left <= 0) throw new IllegalStateException("No conversations remaining");
        user.setNombreConversationsRestantes(left - 1);
        userRepo.save(user);
    }

    @Transactional
    public User applySubscription(User user, SubscriptionType type) {
        user.setAbonnement(type);
        user.setNombreConversationsRestantes(rules.creditsFor(type));
        return userRepo.save(user);
    }
}

