package com.kolosov.corvettetelegrambot.bot.services;

import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.CRYPTO_ORDER_CREATION_ASK_AMOUNT;
import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.CRYPTO_ORDER_CREATION_ASK_CRYPTOCURRENCY;
import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.CRYPTO_ORDER_CREATION_ASK_PRICE;
import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.CRYPTO_ORDER_CREATION_ASK_TYPE;
import static com.kolosov.corvettetelegrambot.bot.dto.BotStateType.IDLE;
import static com.kolosov.corvettetelegrambot.crypto.OrderType.BUY;
import static com.kolosov.corvettetelegrambot.crypto.OrderType.SELL;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import com.kolosov.corvettetelegrambot.crypto.CryptoQuoteService;
import com.kolosov.corvettetelegrambot.crypto.OrderStatus;
import com.kolosov.corvettetelegrambot.crypto.OrderType;
import com.kolosov.corvettetelegrambot.repository.BotStateRepository;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrderCreator {

    private final CryptoOrderRepository cryptoOrderRepository;
    private final BotStateRepository botStateRepository;
    private final PersonalTelegramClient personalTelegramClient;
    private final CryptoQuoteService cryptoQuoteService;

    public static final String BASE = "create_order";
    private static final String CRYPTOCURRENCY = "cryptoCurrency";
    private static final String BTC = "BTC";
    private static final String TON = "TON";
    private static final String TYPE = "type";

    private CryptoOrder cryptoOrder;

    public void startCreation() {
        botStateRepository.setBotState(CRYPTO_ORDER_CREATION_ASK_CRYPTOCURRENCY);
        cryptoOrder = new CryptoOrder();
        cryptoOrder.setCreatedAt(ZonedDateTime.now());
        cryptoOrder.setId(UUID.randomUUID().toString());
        cryptoOrder.setStatus(OrderStatus.OPEN);
        askCryptocurrency();
    }

    private void askCryptocurrency() {
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

        personalTelegramClient.sendWithKeyboard("Select cryptocurrency or write the 3 letter code:", keyboardMarkup);
    }

    private void askType() {
        var buyButton = InlineKeyboardButton.builder()
                .text("BUY")
                .callbackData(BASE + " " + TYPE + " " + BUY)
                .build();

        var sellButton = InlineKeyboardButton.builder()
                .text("SELL")
                .callbackData(BASE + " " + TYPE + " " + SELL)
                .build();

        var keyboardRow = new InlineKeyboardRow(List.of(buyButton, sellButton));

        var keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(keyboardRow)
                .build();

        personalTelegramClient.sendWithKeyboard("Select order type:", keyboardMarkup);
    }

    public void handleCallbackCommands(LinkedList<String> commands) {
        String command = commands.poll();
        switch (command) {
            case CRYPTOCURRENCY -> setCryptocurrency(commands.poll());
            case TYPE -> setType(OrderType.valueOf(commands.poll()));
            default -> {
            }
        }
    }

    public void handleMessage(String text) {
        switch (botStateRepository.getBotState()) {
            case CRYPTO_ORDER_CREATION_ASK_CRYPTOCURRENCY -> setCryptocurrency(text);
            case CRYPTO_ORDER_CREATION_ASK_PRICE -> setPrice(text);
            case CRYPTO_ORDER_CREATION_ASK_AMOUNT -> setAmount(text);
            default -> {
            }
        }
    }

    private void setCryptocurrency(String text) {
        validateCryptocurrency(text);
        cryptoOrder.setCryptoCurrency(text);
        botStateRepository.setBotState(CRYPTO_ORDER_CREATION_ASK_TYPE);
        askType();
    }

    private void validateCryptocurrency(String text) {
        cryptoQuoteService.getQuote(text);
    }

    private void setType(OrderType orderType) {
        cryptoOrder.setType(orderType);
        botStateRepository.setBotState(CRYPTO_ORDER_CREATION_ASK_PRICE);
        askPrice();
    }

    private void askPrice() {
        personalTelegramClient.send("Please enter the price for your order:");
    }

    private void setPrice(String text) {
        BigDecimal price = new BigDecimal(text);
        cryptoOrder.setPrice(price);
        botStateRepository.setBotState(CRYPTO_ORDER_CREATION_ASK_AMOUNT);
        askAmount();
    }

    private void askAmount() {
        personalTelegramClient.send("Please enter the amount for your order:");
    }

    private void setAmount(String text) {
        BigDecimal amount = new BigDecimal(text);
        cryptoOrder.setAmount(amount);
        botStateRepository.setBotState(IDLE);
        cryptoOrderRepository.save(cryptoOrder);
        personalTelegramClient.send("Order created successfully:\n" + cryptoOrder.toString());
    }
}