package com.codesensei.api.service;

import com.codesensei.api.domain.Conversation;
import com.codesensei.api.domain.User;
import com.codesensei.api.repo.ConversationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConversationService {
    private final ConversationRepository convoRepo;
    private final AiClient aiClient;
    private final UserService userService;

    public ConversationService(ConversationRepository convoRepo, AiClient aiClient, UserService userService) {
        this.convoRepo = convoRepo;
        this.aiClient = aiClient;
        this.userService = userService;
    }

    public Page<Conversation> list(User user, Pageable pageable) {
        return convoRepo.findByUserOrderByCreatedAtDesc(user, pageable);
    }

    @Transactional
    public Conversation chat(User user, String message) {
        userService.decrementConversation(user);
        String response = aiClient.chat(message);
        Conversation c = new Conversation();
        c.setUser(user);
        c.setMessage(message);
        c.setResponse(response);
        return convoRepo.save(c);
    }
}

