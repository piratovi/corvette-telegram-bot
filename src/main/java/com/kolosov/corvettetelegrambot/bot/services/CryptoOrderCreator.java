package com.kolosov.corvettetelegrambot.bot.services;

import org.springframework.stereotype.Service;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoOrderCreator {

    private final CryptoOrderRepository cryptoOrderRepository;
    private final PersonalTelegramClient personalTelegramClient;

    public static final String BASE = "create_order";
    private static final String CRYPTOCURRENCY = "cryptoCurrency";
    private static final String BTC = "BTC";
    private static final String TON = "TON";
    
    public void startCreation() {

        
        var btcButton = InlineKeyboardButton.builder()
                .text(BTC)
                .callbackData(BASE + " " + CRYPTOCURRENCY + " " + BTC)
                .build();
        
        var tonButton = InlineKeyboardButton.builder()
                .text(TON)
                .callbackData(BASE + " " + CRYPTOCURRENCY + " " + TON)
                .build();
                
        var keyboardRow = new InlineKeyboardRow(List.of(btcButton, tonButton));
        
        var keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(keyboardRow)
                .build();
                
        personalTelegramClient.sendWithKeyboard("Select cryptocurrency:", keyboardMarkup);
    }
}