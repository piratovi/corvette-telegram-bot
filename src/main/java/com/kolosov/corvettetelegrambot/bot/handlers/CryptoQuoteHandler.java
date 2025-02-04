package com.kolosov.corvettetelegrambot.bot.handlers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.CryptoQuoteService;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoQuoteHandler {

    private final CryptoQuoteService cryptoQuoteService;
    private final PersonalTelegramClient personalTelegramClient;

    public void handle(Message message) {
        Optional<String> commandArgument = getArgument(message);
        if (commandArgument.isEmpty()) {
            sendMessageWithTopCoins();
        } else {
            UsdQuote tonQuote = cryptoQuoteService.getTonQuote(commandArgument.get());
            personalTelegramClient.send(tonQuote.toString());
        }
    }

    private void sendMessageWithTopCoins() {
        var tonButton = InlineKeyboardButton.builder()
                .text("TON")
                .callbackData("quote TON")
                .build();

        var btcButton = InlineKeyboardButton.builder()
                .text("BTC")
                .callbackData("quote BTC")
                .build();

        var keyboardRow = new InlineKeyboardRow(List.of(tonButton, btcButton));
        
        var keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(keyboardRow)
                .build();

        personalTelegramClient.sendWithKeyboard("Select cryptocurrency:", keyboardMarkup);
    }
    
    Optional<String> getArgument(Message message) {
        int commandPrefixLength = message.getEntities().getFirst().getLength();
        String text = message.getText();
        if (text.length() > commandPrefixLength) {
            return Optional.of(text.substring(commandPrefixLength + 1));
        }
        return Optional.empty();
    }
}
