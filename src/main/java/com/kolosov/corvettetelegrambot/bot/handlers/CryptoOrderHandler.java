package com.kolosov.corvettetelegrambot.bot.handlers;

import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrderHandler {

        public static final String BASE = "order";
        public static final String COMMAND = "/" + BASE;
        private static final String SHOW_ALL = "Show All";
        private static final String CREATE = "Create";

        private final CryptoOrderRepository cryptoOrderRepository;
        private final PersonalTelegramClient personalTelegramClient;
        private final HandlerHelper handlerHelper;

        public void handle(Message message) {
                var showAllButton = InlineKeyboardButton.builder()
                                .text(SHOW_ALL)
                                .callbackData(BASE + " " + SHOW_ALL)
                                .build();

                var createButton = InlineKeyboardButton.builder()
                                .text(CREATE)
                                .callbackData(BASE + " " + CREATE)
                                .build();

                var keyboardRow = new InlineKeyboardRow(List.of(showAllButton, createButton));

                var keyboardMarkup = InlineKeyboardMarkup.builder()
                                .keyboardRow(keyboardRow)
                                .build();

                personalTelegramClient.sendWithKeyboard("Select action:", keyboardMarkup);
        }

        public void handle(CallbackQuery callback) {
                String command = callback.getData().substring(BASE.length() + 1);
                switch (command) {
                        case SHOW_ALL -> showAll();
                        case CREATE -> create();
                }
        }

        private void create() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'create'");
        }

        private void showAll() {
                // TODO Auto- method stub
                throw new UnsupportedOperationException("Unimplemented method 'showAll'");
        }

}
