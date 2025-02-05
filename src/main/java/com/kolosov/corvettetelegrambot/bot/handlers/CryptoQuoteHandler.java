package com.kolosov.corvettetelegrambot.bot.handlers;

import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.dto.CallbackDTO;
import com.kolosov.corvettetelegrambot.crypto.CryptoQuoteService;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import com.kolosov.corvettetelegrambot.bot.dto.MessageDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoQuoteHandler {

    public static final String BASE = "quote";
    public static final String COMMAND = "/" + BASE;
    private static final String TON = "TON";
    private static final String BTC = "BTC";

    private final CryptoQuoteService cryptoQuoteService;
    private final PersonalTelegramClient personalTelegramClient;

    public void handle(MessageDTO message) {
        message.argument()
                .ifPresentOrElse(
                        this::sendQuoteMessage,
                        this::sendMenuMessage);
    }

    private void sendQuoteMessage(String symbol) {
        UsdQuote quote = cryptoQuoteService.getQuote(symbol);
        personalTelegramClient.send(quote.toString());
    }

    private void sendMenuMessage() {
        var tonButton = InlineKeyboardButton.builder()
                .text(TON)
                .callbackData(BASE + " " + TON)
                .build();

        var btcButton = InlineKeyboardButton.builder()
                .text(BTC)
                .callbackData(BASE + " " + BTC)
                .build();

        var keyboardRow = new InlineKeyboardRow(List.of(tonButton, btcButton));

        var keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(keyboardRow)
                .build();

        personalTelegramClient.sendWithKeyboard("Select cryptocurrency:", keyboardMarkup);
    }

    public void handle(CallbackDTO callback) {
        callback.subcommand()
                .ifPresentOrElse(
                        this::sendQuoteMessage,
                        this::sendMenuMessage);
    }
}
