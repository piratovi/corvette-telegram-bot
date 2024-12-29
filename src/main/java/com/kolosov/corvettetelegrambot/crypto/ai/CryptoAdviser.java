package com.kolosov.corvettetelegrambot.crypto.ai;

import com.kolosov.corvettetelegrambot.crypto.history.CoinHistoryContainer;
import com.kolosov.corvettetelegrambot.crypto.history.CryptoHistoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.kolosov.corvettetelegrambot.crypto.Cryptocurrency.TON;

@Service
@RequiredArgsConstructor
public class CryptoAdviser {

    private static final Logger logger = LoggerFactory.getLogger(CryptoAdviser.class);

    private final ChatClient chatClient;
    private final CryptoHistoryService cryptoHistoryService;
    private final LLMCostCalculator llmCostCalculator;

    @Value("classpath:/crypto-adviser-system-prompt.txt")
    private Resource systemPrompt;

    @Value("classpath:/crypto-adviser-user-prompt.st")
    private Resource userPrompt;

    @SuppressWarnings("DataFlowIssue")
    public String advise(String userMessage) {
        CoinHistoryContainer historyContainer = cryptoHistoryService.getHistory(TON);
        String coinHistory = historyContainer.toString();

        ChatResponse chatResponse = chatClient.prompt()
                .system(systemPrompt)
                .user(u -> {
                    u.text(userPrompt);
                    u.param("userMessage", userMessage);
                    u.param("coinHistory", coinHistory);
                })
                .call()
                .chatResponse();

        BigDecimal llmCallExpense = llmCostCalculator.calculateCost(chatResponse);
        logger.info("LLM Call expense: {} cents", llmCallExpense);
        return chatResponse.getResult().getOutput().getContent();
    }
}
