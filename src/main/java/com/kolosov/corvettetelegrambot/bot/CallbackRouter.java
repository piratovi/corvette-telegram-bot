package com.kolosov.corvettetelegrambot.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoQuoteHandler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class CallbackRouter {

    private final PersonalTelegramClient personalTelegramClient;
    private final CryptoQuoteHandler cryptoQuoteHandler;
    private final CryptoOrderHandler cryptoOrderHandler;

    public void process(CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();
        if (personalTelegramClient.isUserAllowed(user)) {
            route(callbackQuery);
        }
    }

    @SneakyThrows
    private void route(CallbackQuery callback) {
        String data = callback.getData();
        String command = data.split(" ")[0];
        
        switch (command) {
            case CryptoQuoteHandler.BASE -> cryptoQuoteHandler.handle(callback);
            case CryptoOrderHandler.BASE -> cryptoOrderHandler.handle(callback);
            default -> personalTelegramClient.send("Unknown callback command");
        }
    }
}
