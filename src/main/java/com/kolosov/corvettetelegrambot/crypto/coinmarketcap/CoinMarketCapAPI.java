package com.kolosov.corvettetelegrambot.crypto.coinmarketcap;

import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.QuoteResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange(
        url = "https://pro-api.coinmarketcap.com/v1",
        accept = "application/json"
)
public interface CoinMarketCapAPI {

    @GetExchange("/cryptocurrency/quotes/latest")
    QuoteResponse getQuotes(@RequestParam Map<String, Object> requestParams);
}
