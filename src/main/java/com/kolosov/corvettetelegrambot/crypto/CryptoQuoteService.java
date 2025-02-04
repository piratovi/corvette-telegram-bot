package com.kolosov.corvettetelegrambot.crypto;

import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.CoinMarketCapAPI;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.QuoteResponse;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptoQuoteService {

    private final Logger logger = LoggerFactory.getLogger(CryptoQuoteService.class);
    private final CoinMarketCapAPI coinMarketCapAPI;

    public UsdQuote getQuote(String cryptocurrency) {
        cryptocurrency = cryptocurrency.toUpperCase();
        QuoteResponse quoteResponse = coinMarketCapAPI.getQuotes(Map.of("symbol", cryptocurrency));
        UsdQuote usdQuote = quoteResponse.data().coins().get(cryptocurrency).quote().usd();
        logger.info(usdQuote.toString());
        return usdQuote;
    }
}
