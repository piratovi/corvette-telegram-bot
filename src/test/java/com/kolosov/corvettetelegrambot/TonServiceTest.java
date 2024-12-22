package com.kolosov.corvettetelegrambot;

import com.kolosov.corvettetelegrambot.crypto.CoinMarketCapAPIConfiguration;
import com.kolosov.corvettetelegrambot.crypto.TonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {TonService.class, CoinMarketCapAPIConfiguration.class})
@EnableAutoConfiguration
public class TonServiceTest {

    @Autowired
    private TonService tonService;

    @Test
    public void basicGet() {
        double tonQuote = tonService.getTonQuote();
        System.out.println(tonQuote);
    }
}
