package com.codesensei.api.service;

import com.codesensei.api.domain.Payment;
import com.codesensei.api.domain.SubscriptionType;
import com.codesensei.api.domain.User;
import com.codesensei.api.repo.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final SubscriptionRules rules;
    private final UserService userService;

    public PaymentService(PaymentRepository paymentRepo, SubscriptionRules rules, UserService userService) {
        this.paymentRepo = paymentRepo;
        this.rules = rules;
        this.userService = userService;
    }

    public Page<Payment> list(User user, Pageable pageable) {
        return paymentRepo.findByUserOrderByDatePaiementDesc(user, pageable);
    }

    @Transactional
    public Payment pay(User user, SubscriptionType type, String cardNumber) {
        if (type == null || type == SubscriptionType.NONE) throw new IllegalArgumentException("Invalid subscription");
        Payment p = new Payment();
        p.setUser(user);
        p.setTypeAbonnement(type);
        p.setMontant(rules.priceFor(type));
        p.setCardLast4(cardNumber.substring(cardNumber.length() - 4));
        Payment saved = paymentRepo.save(p);
        userService.applySubscription(user, type);
        return saved;
    }
}

