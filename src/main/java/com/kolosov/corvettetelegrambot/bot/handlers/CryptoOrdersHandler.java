package com.kolosov.corvettetelegrambot.bot.handlers;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
@RequiredArgsConstructor
public class CryptoOrdersHandler implements CommandHandler {

    private final CryptoOrderRepository cryptoOrderRepository;
    private final PersonalTelegramClient personalTelegramClient;

    @Override
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
