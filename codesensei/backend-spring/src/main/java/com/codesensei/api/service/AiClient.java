package com.codesensei.api.service;

import com.codesensei.api.config.AppProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AiClient {
    private final RestClient client;

    public AiClient(AppProperties props) {
        this.client = RestClient.builder()
                .baseUrl(props.getAi().getBaseUrl())
                .build();
    }

    public String chat(String message) {
        record Req(String message) {}
        record Res(String response) {}
        Res res = client.post()
                .uri("/v1/cgm/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new Req(message))
                .retrieve()
                .body(Res.class);
        if (res == null || res.response() == null) {
            throw new IllegalStateException("AI service unavailable");
        }
        return res.response();
    }
}

