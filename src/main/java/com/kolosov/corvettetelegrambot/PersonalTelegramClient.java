package com.kolosov.corvettetelegrambot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@RequiredArgsConstructor
public class PersonalTelegramClient {

    private final TelegramClient telegramClient;

    @Value("${ALLOWED_CHAT_ID}")
    private String allowedChatId;

    @SneakyThrows
    public void execute(String message) {
        SendMessage responseMessage = new SendMessage(allowedChatId, message);
        telegramClient.execute(responseMessage);
    }
}
