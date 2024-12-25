package com.kolosov.corvettetelegrambot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Service
@RequiredArgsConstructor
public class CorvetteTelegramBot implements SpringLongPollingBot {

    private final CommandHandler commandHandler;

    @Value("${CORVETTE_TELEGRAM_BOT_TOKEN}")
    private String token;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return commandHandler;
    }
}
