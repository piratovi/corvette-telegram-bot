package com.kolosov.corvettetelegrambot;

import com.kolosov.corvettetelegrambot.configuration.DynamoDBConfig;
import com.kolosov.corvettetelegrambot.crypto.ai.CryptoAdviser;
import com.kolosov.corvettetelegrambot.crypto.ai.LLMCostCalculator;
import com.kolosov.corvettetelegrambot.crypto.ai.LLMCostProperties;
import com.kolosov.corvettetelegrambot.crypto.ai.configuration.ChatClientConfiguration;
import com.kolosov.corvettetelegrambot.crypto.cryptocompare.CryptocompareAPIConfiguration;
import com.kolosov.corvettetelegrambot.crypto.history.CryptoHistoryService;
import com.kolosov.corvettetelegrambot.crypto.history.mapper.CryptoHistoryMapper;
import com.kolosov.corvettetelegrambot.crypto.history.mapper.CryptoHistoryMapperImpl;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {CryptoHistoryService.class, CryptocompareAPIConfiguration.class,
        CryptoHistoryMapper.class, CryptoHistoryMapperImpl.class, CryptoAdviser.class, ChatClientConfiguration.class,
        LLMCostCalculator.class, LLMCostCalculator.class, CryptoOrderRepository.class, DynamoDBConfig.class})
@EnableAutoConfiguration
@EnableConfigurationProperties(LLMCostProperties.class)
@ActiveProfiles("dev")
public class CryptoAdvisorTest {

    @Autowired
    private CryptoAdviser cryptoAdviser;

    @Test
    public void basicGet() {
        String history = cryptoAdviser.advise();
        System.out.println(history);
    }
}
