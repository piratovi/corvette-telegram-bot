package com.kolosov.corvettetelegrambot.bot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service

@RequiredArgsConstructor
public class UpdateRouter implements LongPollingUpdateConsumer {

    protected final Logger logger = LoggerFactory.getLogger(UpdateRouter.class);

    private final TelegramClient telegramClient;
    private final PersonalTelegramClient personalTelegramClient;
    private final MessageRouter messageRouter;
    private final CallbackQueryRouter callbackQueryRouter;

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
        updates.forEach(this::processReceivedUpdateWithExceptionHandling);
    }

    private void processReceivedUpdateWithExceptionHandling(Update update) {
        try {
            process(update);
        } catch (Exception e) {
            logger.error(e.getMessage());
            personalTelegramClient.send(e.getMessage());
        }
    }

    private void process(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageRouter.process(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            callbackQueryRouter.handle(update.getCallbackQuery());
        }
    }

}
