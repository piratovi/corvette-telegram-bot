package com.kolosov.corvettetelegrambot.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.CryptoQuoteService;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class CallbackQueryRouter {

    private final PersonalTelegramClient personalTelegramClient;
    private final CryptoQuoteService cryptoQuoteService;

    public void handle(CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();
        if (personalTelegramClient.isUserAllowed(user)) {
            route(callbackQuery);
        }
    }

    @SneakyThrows
    private void route(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        if (data.startsWith("quote ")) {
            String symbol = data.substring(6); // Skip "quote " prefix
            UsdQuote quote = cryptoQuoteService.getTonQuote(symbol);
            personalTelegramClient.send(quote.toString());
        }
    }
}
