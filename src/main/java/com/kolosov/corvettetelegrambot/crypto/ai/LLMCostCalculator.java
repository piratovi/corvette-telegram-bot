package com.kolosov.corvettetelegrambot.crypto.ai;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.autoconfigure.bedrock.converse.BedrockConverseProxyChatProperties;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LLMCostCalculator {

    protected final Logger logger = LoggerFactory.getLogger(LLMCostCalculator.class);

    private final LLMCostProperties llmCostProperties;
    private final BedrockConverseProxyChatProperties chatProperties;

    //returns expenses in cents
    public BigDecimal calculateCost(ChatResponse chatResponse) {
        Usage usage = chatResponse.getMetadata().getUsage();

        BigDecimal inputTokens = BigDecimal.valueOf(usage.getPromptTokens());
        BigDecimal outputTokens = BigDecimal.valueOf(usage.getGenerationTokens());
        logger.info("Input tokens: {}", inputTokens);
        logger.info("Output tokens: {}", outputTokens);

        String modelName = getModelName();
        BigDecimal inputTokensCost = llmCostProperties.input().get(modelName).multiply(inputTokens);
        BigDecimal outputTokensCost = llmCostProperties.output().get(modelName).multiply(outputTokens);

        return inputTokensCost.add(outputTokensCost);
    }

    @SuppressWarnings("DataFlowIssue")
    private String getModelName() {
        String model = chatProperties.getOptions().getModel();
        if (model.contains("sonnet")) {
            return "sonnet";
        } else if (model.contains("opus")) {
            return "opus";
        }
        throw new RuntimeException("No such model: " + model);
    }
}