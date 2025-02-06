package com.kolosov.corvettetelegrambot.bot;

import java.util.Collections;
import java.util.LinkedList;

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
    private void route(CallbackQuery callbackQuery) {
        LinkedList<String> commands = parseCallbackQuery(callbackQuery.getData());

        String command = commands.removeFirst();
        switch (command) {
            case CryptoQuoteHandler.BASE -> cryptoQuoteHandler.handleCallbackCommands(commands);
            case CryptoOrderHandler.BASE -> cryptoOrderHandler.handleCallbackCommands(commands);
            default -> personalTelegramClient.send("Unknown callback command");
        }
    }

    private LinkedList<String> parseCallbackQuery(String data) {
        String[] parts = data.split(" ");
        LinkedList<String> commands = new LinkedList<>();
        Collections.addAll(commands, parts);
        return commands;
    }
}
