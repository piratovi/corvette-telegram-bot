package com.kolosov.corvettetelegrambot.bot.handlers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.bot.dto.MessageDTO;
import com.kolosov.corvettetelegrambot.bot.services.CryptoOrderCreator;
import com.kolosov.corvettetelegrambot.bot.services.CryptoOrderDisplayService;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrderHandler {

        public static final String BASE = "order";
        public static final String COMMAND = "/" + BASE;

        public static final String SHOW_ALL = "show_all";
        public static final String CREATE = "start_creation";
        public static final String EDIT = "edit";
        public static final String DELETE = "delete";

        private final CryptoOrderRepository cryptoOrderRepository;
        private final PersonalTelegramClient personalTelegramClient;
        private final CryptoOrderCreator cryptoOrderCreator;
        private final CryptoOrderDisplayService cryptoOrderDisplayService;

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
                        case SHOW_ALL -> showAll();
                        case CREATE -> create();
                        case EDIT -> edit(commands);
                        case DELETE -> delete(commands);
                }
        }

        private void showAll() {
                List<CryptoOrder> orders = cryptoOrderRepository.findAllOpen();
                orders.forEach(this::show);
        }

        private void show(CryptoOrder order) {
                cryptoOrderDisplayService.displayOrder(order, "");
        }

        private void create() {
                cryptoOrderCreator.startCreation();
        }

        private void delete(LinkedList<String> commands) {
                String id = commands.removeFirst();
                cryptoOrderRepository.deleteById(id);
        }


        private void edit(LinkedList<String> commands) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'edit'");
        }

}
