package com.kolosov.corvettetelegrambot;

import com.kolosov.corvettetelegrambot.crypto.CryptoQuoteService;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.CoinMarketCapAPIConfiguration;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kolosov.corvettetelegrambot.crypto.Cryptocurrency.TON;

@SpringBootTest(classes = {CryptoQuoteService.class, CoinMarketCapAPIConfiguration.class})
@EnableAutoConfiguration
public class CryptoQuoteServiceTest {

    @Autowired
    private CryptoQuoteService cryptoQuoteService;

    @Test
    public void basicGet() {
        UsdQuote tonQuote = cryptoQuoteService.getTonQuote(TON.name());
        System.out.println(tonQuote);
    }
}
