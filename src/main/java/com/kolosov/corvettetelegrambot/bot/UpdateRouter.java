package com.kolosov.corvettetelegrambot.bot;

import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UpdateRouter implements LongPollingUpdateConsumer {

    protected final Logger logger = LoggerFactory.getLogger(UpdateRouter.class);

    private final PersonalTelegramClient personalTelegramClient;
    private final MessageRouter messageRouter;
    private final CallbackRouter callbackQueryRouter;
    private final CommandRegistrationService commandRegistrationService;
    

    @PostConstruct
    private void init() {
        commandRegistrationService.registerCommands();
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(this::processReceivedUpdateWithExceptionHandling);
    }

    private void processReceivedUpdateWithExceptionHandling(Update update) {
        try {
            process(update);
        } catch (Exception e) {
            logger.error("error", e);
            personalTelegramClient.send(e.getMessage());
        }
    }

    private void process(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageRouter.process(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            callbackQueryRouter.process(update.getCallbackQuery());
        }
    }

}
