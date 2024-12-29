package com.kolosov.corvettetelegrambot.crypto.ai;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.Map;

@ConfigurationProperties(prefix = "calculator.cent.cost.token")
public record LLMCostProperties(
        Map<String, BigDecimal> input,
        Map<String, BigDecimal> output) {
}