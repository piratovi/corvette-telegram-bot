package com.kolosov.corvettetelegrambot.bot.handlers;

import java.util.List;
import java.util.LinkedList;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.dto.CallbackDTO;
import com.kolosov.corvettetelegrambot.bot.dto.MessageDTO;
import com.kolosov.corvettetelegrambot.bot.services.CryptoOrderCreator;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrderHandler {

        public static final String BASE = "order";
        public static final String COMMAND = "/" + BASE;

        private static final String SHOW_ALL = "show_all";
        private static final String CREATE = "create";
        private static final String EDIT = "edit";
        private static final String DELETE = "delete";

        private final CryptoOrderRepository cryptoOrderRepository;
        private final PersonalTelegramClient personalTelegramClient;
        private final CryptoOrderCreator cryptoOrderCreator;

        public void handle(MessageDTO message) {
                sendMenuMessage();
        }

        private void sendMenuMessage() {
                var showAllButton = InlineKeyboardButton.builder()
                                .text("Show all")
                                .callbackData(BASE + " " + SHOW_ALL)
                                .build();

                var createButton = InlineKeyboardButton.builder()
                                .text("Create")
                                .callbackData(BASE + " " + CREATE)
                                .build();

                var keyboardRow = new InlineKeyboardRow(List.of(showAllButton, createButton));

                var keyboardMarkup = InlineKeyboardMarkup.builder()
                                .keyboardRow(keyboardRow)
                                .build();

                personalTelegramClient.sendWithKeyboard("Select action:", keyboardMarkup);
        }

        public void handleCallbackCommands(LinkedList<String> commands) {
                String subcommand = commands.removeFirst();
                switch (subcommand) {
                        case SHOW_ALL   -> showAll();
                        case CREATE     -> create();
                        case EDIT       -> edit(commands);
                        case DELETE     -> delete(commands.removeFirst());
                }
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
        
        private void create() {
                cryptoOrderCreator.startCreation();
        }
        
        private void delete(String id) {
                // TODO: erase message from chat
                cryptoOrderRepository.deleteById(id);
        }

        private void edit(LinkedList<String> commands) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'edit'");
        }


        



}
