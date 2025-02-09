package com.kolosov.corvettetelegrambot.bot;

import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.CRYPTO_ORDER_CREATION_ASK_CRYPTOCURRENCY;
import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.IDLE;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.dto.MessageDTO;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoQuoteHandler;
import com.kolosov.corvettetelegrambot.bot.services.CryptoOrderCreator;
import com.kolosov.corvettetelegrambot.repository.BotStateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageRouter {

    private final CryptoQuoteHandler cryptoQuoteHandler;
    private final PersonalTelegramClient personalTelegramClient;
    private final CryptoOrderHandler cryptoOrdersHandler;
    private final BotStateRepository botStateRepository;
    private final CryptoOrderCreator cryptoOrderCreator;

    public void process(Message message) {
        User user = message.getFrom();
        if (personalTelegramClient.isUserAllowed(user)) {
            route(message);
        }
    }

    private void route(Message message) {
        String text = message.getText();
        if (botStateRepository.getBotState() != IDLE) {
            cryptoOrderCreator.handleMessage(text);
        } else {
            routeCommand(text);
        }
    }

    private void routeCommand(String text) {
        MessageDTO messageDto = parseMessage(text);
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
                parts.length > 1 ? Optional.of(parts[1]) : Optional.empty());
    }
}
