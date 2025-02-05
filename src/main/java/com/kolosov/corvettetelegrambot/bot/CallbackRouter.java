package com.kolosov.corvettetelegrambot.bot;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoQuoteHandler;
import com.kolosov.corvettetelegrambot.bot.dto.CallbackDTO;

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
    private void route(CallbackQuery callbackQuery) {
        CallbackDTO callback = parseCallback(callbackQuery.getData());
        
        switch (callback.command()) {
            case CryptoQuoteHandler.BASE -> cryptoQuoteHandler.handle(callback);
            case CryptoOrderHandler.BASE -> cryptoOrderHandler.handle(callback);
            default -> personalTelegramClient.send("Unknown callback command");
        }
    }

    private CallbackDTO parseCallback(String data) {
        String[] parts = data.split(" ", 3);
        return new CallbackDTO(
            parts[0],
            parts.length > 1 ? Optional.of(parts[1]) : Optional.empty(),
            parts.length > 2 ? Optional.of(parts[2]) : Optional.empty()
        );
    }
}
