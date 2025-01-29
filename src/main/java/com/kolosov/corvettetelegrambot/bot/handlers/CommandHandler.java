package com.kolosov.corvettetelegrambot.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handle(Update update);
}
