package com.kolosov.corvettetelegrambot.bot.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrdersHandler {

    private final CryptoOrderRepository cryptoOrderRepository;
    private final PersonalTelegramClient personalTelegramClient;

    public void handle(Update update) {
        // String commandArgument = getArgument(update);
        // UsdQuote tonQuote = cryptoQuoteService.getTonQuote(commandArgument);
        // personalTelegramClient.send(tonQuote.toString());
    }

    private String getArgument(Update update) {
        Message message = update.getMessage();
        int commandPrefixLength = message.getEntities().getFirst().getLength();
        return message.getText().substring(commandPrefixLength + 1);
    }
}
