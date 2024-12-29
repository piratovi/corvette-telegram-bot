package com.kolosov.corvettetelegrambot.crypto.ai;

import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LLMCostCalculator {

    @Value("${calculator.sonnet3.5.token.centcost.input}")
    private BigDecimal centPerInputToken;
    @Value("${calculator.sonnet3.5.token.centcost.output}")
    private BigDecimal centPerOutputToken;

    //returns expenses in cents
    public BigDecimal calculateCost(ChatResponse chatResponse) {
        Usage usage = chatResponse.getMetadata().getUsage();

        BigDecimal inputTokens = BigDecimal.valueOf(usage.getPromptTokens());
        BigDecimal outputTokens = BigDecimal.valueOf(usage.getGenerationTokens());

        BigDecimal inputTokensCost = centPerInputToken.multiply(inputTokens);
        BigDecimal outputTokensCost = centPerOutputToken.multiply(outputTokens);

        return inputTokensCost.add(outputTokensCost);
    }
}