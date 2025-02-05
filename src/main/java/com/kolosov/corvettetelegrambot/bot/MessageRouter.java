package com.kolosov.corvettetelegrambot.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoQuoteHandler;
import com.kolosov.corvettetelegrambot.bot.dto.MessageDTO;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageRouter {

    private final CryptoQuoteHandler cryptoQuoteHandler;
    private final PersonalTelegramClient personalTelegramClient;
    private final CryptoOrderHandler cryptoOrdersHandler;

    public void process(Message message) {
        User user = message.getFrom();
        if (personalTelegramClient.isUserAllowed(user)) {
            route(message);
        }
    }

    private void route(Message message) {
        MessageDTO messageDto = parseMessage(message.getText());
        
        switch (messageDto.command()) {
            case CryptoQuoteHandler.COMMAND -> cryptoQuoteHandler.handle(messageDto);
            case CryptoOrderHandler.COMMAND -> cryptoOrdersHandler.handle(messageDto);
            default -> personalTelegramClient.send("Wrong command");
        }
    }

    private MessageDTO parseMessage(String text) {
        String[] parts = text.split(" ", 2);
        return new MessageDTO(
            parts[0],
            parts.length > 1 ? Optional.of(parts[1]) : Optional.empty()
        );
    }
}
