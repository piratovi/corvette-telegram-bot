package com.kolosov.corvettetelegrambot.crypto;

import com.kolosov.corvettetelegrambot.crypto.dto.QuoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TonService {

    private final CoinMarketCapAPI coinMarketCapAPI;

    public double getTonQuote() {
        QuoteResponse quoteResponse = coinMarketCapAPI.getQuotes(List.of("TON"));
        return quoteResponse.data().coins().get("TON").quote().usd().price();

    }
}
