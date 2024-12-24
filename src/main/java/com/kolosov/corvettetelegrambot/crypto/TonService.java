package com.kolosov.corvettetelegrambot.crypto;

import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.CoinMarketCapAPI;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.QuoteResponse;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TonService {

    private final Logger logger = LoggerFactory.getLogger(TonService.class);
    private final CoinMarketCapAPI coinMarketCapAPI;

    public UsdQuote getTonQuote() {
        QuoteResponse quoteResponse = coinMarketCapAPI.getQuotes(List.of("TON"));
        UsdQuote usdQuote = quoteResponse.data().coins().get("TON").quote().usd();
        logger.info(usdQuote.toString());
        return usdQuote;

    }
}
