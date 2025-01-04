package com.kolosov.corvettetelegrambot.bot;

import com.kolosov.corvettetelegrambot.crypto.TonService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandHandler implements LongPollingUpdateConsumer {

    private final TelegramClient telegramClient;
    private final TonService tonService;

    @Value("${ALLOWED_USER_ID}")
    private Long allowedUserId;

    @SneakyThrows
    @PostConstruct
    private void registerCommands() {
        SetMyCommands setMyCommands = SetMyCommands.builder()
                .command(BotCommand.builder().command("ton").description("TON quotes").build())
                .build();
        telegramClient.execute(setMyCommands);
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasMessage() && update.getMessage().hasText()) {
                processReceivedMessage(update.getMessage());
            }
        });
    }

    @SneakyThrows
    private void processReceivedMessage(Message userMessage) {
        Long userId = userMessage.getFrom().getId();
        if (allowedUserId.equals(userId)) {
            String responseText = generateReply(userMessage.getText());
            String chatId = userMessage.getChatId().toString();
            SendMessage responseMessage = new SendMessage(chatId, responseText);
            telegramClient.execute(responseMessage);
        }
    }

    private String generateReply(String requestText) {
        switch (requestText) {
            case "/ton":
                return String.valueOf(tonService.getTonQuote());
            default: {
                return "Wrong command";
            }
        }
    }

}
