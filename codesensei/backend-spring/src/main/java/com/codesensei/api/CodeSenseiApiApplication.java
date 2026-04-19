package com.codesensei.api;

import com.codesensei.api.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class CodeSenseiApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeSenseiApiApplication.class, args);
    }
}

