package com.codesensei.api.web.dto;

import com.codesensei.api.domain.Payment;
import com.codesensei.api.domain.SubscriptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PaymentDtos {
    public record PaymentRequest(
            @NotNull SubscriptionType typeAbonnement,
            @NotBlank @Pattern(regexp = "^[0-9]{16}$") String cardNumber,
            @NotBlank @Pattern(regexp = "^[0-9]{2}/[0-9]{2}$") String expiry, // MM/YY
            @NotBlank @Pattern(regexp = "^[0-9]{3,4}$") String cvc
    ) {}

    public record PaymentResponse(
            long id,
            SubscriptionType typeAbonnement,
            BigDecimal montant,
            OffsetDateTime datePaiement,
            String cardLast4
    ) {
        public static PaymentResponse from(Payment p) {
            return new PaymentResponse(p.getId(), p.getTypeAbonnement(), p.getMontant(), p.getDatePaiement(), p.getCardLast4());
        }
    }
}

