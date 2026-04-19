package com.codesensei.api.repo;

import com.codesensei.api.domain.Payment;
import com.codesensei.api.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByUserOrderByDatePaiementDesc(User user, Pageable pageable);
}

