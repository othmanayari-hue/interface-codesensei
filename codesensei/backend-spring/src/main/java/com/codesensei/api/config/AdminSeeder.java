package com.codesensei.api.config;

import com.codesensei.api.domain.SubscriptionType;
import com.codesensei.api.domain.User;
import com.codesensei.api.domain.UserRole;
import com.codesensei.api.repo.UserRepository;
import com.codesensei.api.service.SubscriptionRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {
    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    @Bean
    public ApplicationRunner seedAdmin(AppProperties props, UserRepository userRepo, PasswordEncoder encoder, SubscriptionRules rules) {
        return args -> {
            String email = props.getAdmin().getEmail().trim().toLowerCase();
            if (userRepo.existsByEmailIgnoreCase(email)) return;

            User admin = new User();
            admin.setNom("Admin");
            admin.setPrenom("CodeSensei");
            admin.setEmail(email);
            admin.setRole(UserRole.ADMIN);
            admin.setAbonnement(SubscriptionType.PRO_PLUS);
            admin.setNombreConversationsRestantes(rules.creditsFor(SubscriptionType.PRO_PLUS));
            admin.setPassword(encoder.encode(props.getAdmin().getPassword()));
            userRepo.save(admin);
            log.info("Seeded admin user: {}", email);
        };
    }
}

