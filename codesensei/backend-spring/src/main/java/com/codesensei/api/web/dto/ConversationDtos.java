package com.codesensei.api.web.dto;

import com.codesensei.api.domain.Conversation;
import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public class ConversationDtos {
    public record ChatRequest(@NotBlank String message) {}

    public record ConversationResponse(
            long id,
            String message,
            String response,
            OffsetDateTime createdAt
    ) {
        public static ConversationResponse from(Conversation c) {
            return new ConversationResponse(c.getId(), c.getMessage(), c.getResponse(), c.getCreatedAt());
        }
    }
}

