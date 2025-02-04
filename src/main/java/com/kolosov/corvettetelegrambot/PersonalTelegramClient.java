package com.kolosov.corvettetelegrambot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class PersonalTelegramClient {

    protected final Logger logger = LoggerFactory.getLogger(PersonalTelegramClient.class);

    private final TelegramClient telegramClient;

    @Value("${ALLOWED_CHAT_ID}")
    @Getter
    private String allowedChatId;

    @Value("${MONITORING_MESSAGE_ID}")
    private String monitoringMessageId;

    @Value("${ALLOWED_USER_ID}")
    private Long allowedUserId;

    @SneakyThrows
    public void send(String message) {
        SendMessage responseMessage = new SendMessage(allowedChatId, message);
        telegramClient.execute(responseMessage);
    }

    public void refreshMonitoringMessage(String message) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(allowedChatId)
                .messageId(Integer.parseInt(monitoringMessageId))
                .text(message)
                .build();
        try {
            telegramClient.execute(editMessageText);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean isUserAllowed(User user) {
        return allowedUserId.equals(user.getId());
    }

    @SneakyThrows
    public void sendWithKeyboard(String text, InlineKeyboardMarkup keyboardMarkup) {
        var message = SendMessage.builder()
                .chatId(allowedChatId)
                .text(text)
                .replyMarkup(keyboardMarkup)
                .build();
        telegramClient.execute(message);
    }
}
