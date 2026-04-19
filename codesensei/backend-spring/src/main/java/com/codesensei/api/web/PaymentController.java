package com.codesensei.api.web;

import com.codesensei.api.domain.User;
import com.codesensei.api.repo.UserRepository;
import com.codesensei.api.service.PaymentService;
import com.codesensei.api.web.dto.PaymentDtos;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepo;

    public PaymentController(PaymentService paymentService, UserRepository userRepo) {
        this.paymentService = paymentService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public Page<PaymentDtos.PaymentResponse> list(Authentication auth,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        User u = userRepo.findByEmailIgnoreCase(auth.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return paymentService.list(u, PageRequest.of(page, size))
                .map(PaymentDtos.PaymentResponse::from);
    }

    @PostMapping
    public PaymentDtos.PaymentResponse pay(Authentication auth, @Valid @RequestBody PaymentDtos.PaymentRequest req) {
        User u = userRepo.findByEmailIgnoreCase(auth.getName()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return PaymentDtos.PaymentResponse.from(paymentService.pay(u, req.typeAbonnement(), req.cardNumber()));
    }
}

