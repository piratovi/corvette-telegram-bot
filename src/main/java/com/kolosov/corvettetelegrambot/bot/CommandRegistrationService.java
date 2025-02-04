package com.kolosov.corvettetelegrambot.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import com.kolosov.corvettetelegrambot.bot.handlers.CryptoOrderHandler;
import com.kolosov.corvettetelegrambot.bot.handlers.CryptoQuoteHandler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandRegistrationService {
    
    private final TelegramClient telegramClient;

    @SneakyThrows
    public void registerCommands() {
        List<BotCommand> commands = preparePredefinedCommands();
        SetMyCommands setMyCommands = SetMyCommands.builder()
                .commands(commands)
                .build();
        telegramClient.execute(setMyCommands);
    }

    private List<BotCommand> preparePredefinedCommands() {
        BotCommand quoteCommand = BotCommand.builder()
                .command(CryptoQuoteHandler.BASE)
                .description("crypto quotes")
                .build();
        BotCommand orderCommand = BotCommand.builder()
                .command(CryptoOrderHandler.BASE)
                .description("crypto orders")
                .build();
        return List.of(quoteCommand, orderCommand);
    }
} 