package com.kolosov.corvettetelegrambot;

import com.kolosov.corvettetelegrambot.crypto.cryptocompare.CryptocompareAPIConfiguration;
import com.kolosov.corvettetelegrambot.crypto.history.CoinHistoryContainer;
import com.kolosov.corvettetelegrambot.crypto.history.CryptoHistoryService;
import com.kolosov.corvettetelegrambot.crypto.history.mapper.CryptoHistoryMapper;
import com.kolosov.corvettetelegrambot.crypto.history.mapper.CryptoHistoryMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.kolosov.corvettetelegrambot.crypto.Cryptocurrency.TON;

@SpringBootTest(classes = {CryptoHistoryService.class, CryptocompareAPIConfiguration.class, CryptoHistoryMapper.class, CryptoHistoryMapperImpl.class})
@EnableAutoConfiguration
@ActiveProfiles("dev")
public class CoinHistoryServiceTest {

    @Autowired
    private CryptoHistoryService cryptoHistoryService;

    @Test
    public void basicGet() {
        CoinHistoryContainer history = cryptoHistoryService.getHistory(TON);
        history.dailyHistory().forEach(System.out::println);
        history.hourlyHistory().forEach(System.out::println);
        history.minutesHistory().forEach(System.out::println);
    }
}
