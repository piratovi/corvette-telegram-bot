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
import com.kolosov.corvettetelegrambot.bot.dto.MessageDTO;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrderHandler {

        public static final String EDIT = "Edit";
        public static final String DELETE = "Delete";
        public static final String BASE = "order";
        public static final String COMMAND = "/" + BASE;
        private static final String SHOW_ALL = "Show All";
        private static final String CREATE = "Create";

        private final CryptoOrderRepository cryptoOrderRepository;
        private final PersonalTelegramClient personalTelegramClient;

        public void handle(MessageDTO message) {
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

        public void handle(CallbackDTO callback) {
                callback.subcommand().ifPresent(subcommand -> {
                        switch (subcommand) {
                                case SHOW_ALL -> showAll();
                                case CREATE -> create();
                                case EDIT -> edit(callback.argument().orElseThrow());
                                case DELETE -> delete(callback.argument().orElseThrow());
                        }
                });
        }

        private void delete(String id) {
                cryptoOrderRepository.deleteById(id);
        }

        private Object edit(String data) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'edit'");
        }

        private void create() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'create'");
        }

        private void showAll() {
                List<CryptoOrder> orders = cryptoOrderRepository.findAll();
                orders.forEach(order -> show(order));
        }

        private void show(CryptoOrder order) {
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

                personalTelegramClient.sendWithKeyboard(order.toString(), keyboardMarkup);
        }

}
