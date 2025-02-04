package com.kolosov.corvettetelegrambot.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoQuoteHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageRouter {

    private final CryptoQuoteHandler cryptoQuoteHandler;
    private final PersonalTelegramClient personalTelegramClient;

    public void process(Message message) {
        User user = message.getFrom();
        if (personalTelegramClient.isUserAllowed(user)) {
            route(message);
        }
    }

    private void route(Message message) {
        String requestText = message.getText();
        if (requestText.startsWith("/quote")) {
            cryptoQuoteHandler.handle(message);
            return;
        }
        if (requestText.startsWith("/orders")) {
            // orders handling
            return;
        }
        personalTelegramClient.send("Wrong command");
    }
}
