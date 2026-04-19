package com.codesensei.api.repo;

import com.codesensei.api.domain.Conversation;
import com.codesensei.api.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Page<Conversation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}

