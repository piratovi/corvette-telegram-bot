package com.kolosov.corvettetelegrambot.bot.services;

import static com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler.BASE;
import static com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler.EDIT;
import static com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler.DELETE;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoOrderDisplayService {

    private final PersonalTelegramClient personalTelegramClient;

    public void displayOrder(CryptoOrder order, String message) {
        var editButton = InlineKeyboardButton.builder()
                .text(EDIT)
                .callbackData(BASE + " " + EDIT + " " + order.getId())
                .build();

        var deleteButton = InlineKeyboardButton.builder()
                .text(DELETE)
                .callbackData(BASE + " " + DELETE + " " + order.getId())
                .build();

        var keyboardRow = new InlineKeyboardRow(List.of(editButton, deleteButton));

        var keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(keyboardRow)
                .build();

        personalTelegramClient.sendWithKeyboard(message + order.toString(), keyboardMarkup);
    }
} 