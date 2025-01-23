package com.kolosov.corvettetelegrambot.bot;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandRouter implements LongPollingUpdateConsumer {

    protected final Logger logger = LoggerFactory.getLogger(CommandRouter.class);

    private final TelegramClient telegramClient;
    private final PersonalTelegramClient personalTelegramClient;
    private final CryptoQuoteHandler cryptoQuoteHandler;

    @SneakyThrows
    @PostConstruct
    private void registerCommands() {
        List<BotCommand> commands = preparePredefinedCommands();
        SetMyCommands setMyCommands = SetMyCommands.builder()
                .commands(commands)
                .build();
        telegramClient.execute(setMyCommands);
    }

    private List<BotCommand> preparePredefinedCommands() {
        BotCommand command = BotCommand.builder()
                .command("quote")
                .description("crypto quotes")
                .build();
        return List.of(command);
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasMessage() && update.getMessage().hasText()) {
                processReceivedUpdateWithExceptionHandling(update);
            }
        });
    }

    private void processReceivedUpdateWithExceptionHandling(Update update) {
        try {
            processReceivedMessage(update);
        } catch (Exception e) {
            logger.error(e.getMessage());
            personalTelegramClient.send(e.getMessage());
        }
    }

    private void processReceivedMessage(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        if (personalTelegramClient.isAllowedUser(userId)) {
            route(update);
        }
    }

    private void route(Update update) {
        String requestText = update.getMessage().getText();
        if (requestText.startsWith("/quote")) {
            cryptoQuoteHandler.handle(update);
            return;
        }
        if (requestText.startsWith("/orders")) {
            // orders handling
            return;
        }
        personalTelegramClient.send("Wrong command");
    }
}
