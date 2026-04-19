package com.codesensei.api.web;

import com.codesensei.api.domain.User;
import com.codesensei.api.repo.UserRepository;
import com.codesensei.api.service.ConversationService;
import com.codesensei.api.web.dto.ConversationDtos;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;
    private final UserRepository userRepo;

    public ConversationController(ConversationService conversationService, UserRepository userRepo) {
        this.conversationService = conversationService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public Page<ConversationDtos.ConversationResponse> list(Authentication auth,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        User u = userRepo.findByEmailIgnoreCase(auth.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return conversationService.list(u, PageRequest.of(page, size))
                .map(ConversationDtos.ConversationResponse::from);
    }

    @PostMapping("/chat")
    public ConversationDtos.ConversationResponse chat(Authentication auth, @Valid @RequestBody ConversationDtos.ChatRequest req) {
        User u = userRepo.findByEmailIgnoreCase(auth.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ConversationDtos.ConversationResponse.from(conversationService.chat(u, req.message()));
    }
}

