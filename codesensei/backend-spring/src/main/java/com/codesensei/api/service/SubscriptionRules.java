package com.codesensei.api.service;

import com.codesensei.api.domain.SubscriptionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

@Component
public class SubscriptionRules {
    private final Map<SubscriptionType, Integer> conversationCredits = new EnumMap<>(SubscriptionType.class);
    private final Map<SubscriptionType, BigDecimal> prices = new EnumMap<>(SubscriptionType.class);

    public SubscriptionRules() {
        conversationCredits.put(SubscriptionType.NONE, 1);
        conversationCredits.put(SubscriptionType.PRO, 3);
        conversationCredits.put(SubscriptionType.BUSINESS, 7);
        conversationCredits.put(SubscriptionType.PRO_PLUS, 10);

        prices.put(SubscriptionType.PRO, new BigDecimal("10.00"));
        prices.put(SubscriptionType.BUSINESS, new BigDecimal("15.00"));
        prices.put(SubscriptionType.PRO_PLUS, new BigDecimal("20.00"));
    }

    public int creditsFor(SubscriptionType type) {
        return conversationCredits.getOrDefault(type, 1);
    }

    public BigDecimal priceFor(SubscriptionType type) {
        BigDecimal price = prices.get(type);
        if (price == null) throw new IllegalArgumentException("Unsupported subscription");
        return price;
    }
}

