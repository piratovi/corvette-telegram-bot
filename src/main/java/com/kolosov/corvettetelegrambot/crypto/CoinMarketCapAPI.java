package com.kolosov.corvettetelegrambot.crypto;

import com.kolosov.corvettetelegrambot.crypto.dto.QuoteResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(
        url = "https://pro-api.coinmarketcap.com/v1",
        accept = "application/json"
)
public interface CoinMarketCapAPI {

    @GetExchange("/cryptocurrency/quotes/latest?symbol={coins}")
    QuoteResponse getQuotes(@PathVariable List<String> coins);
}
