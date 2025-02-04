package com.kolosov.corvettetelegrambot.bot.handlers;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HandlerHelper {

    Optional<String> getArgument(Message message) {
        int commandPrefixLength = message.getEntities().getFirst().getLength();
        String text = message.getText();
        if (text.length() > commandPrefixLength) {
            return Optional.of(text.substring(commandPrefixLength + 1));
        }
        return Optional.empty();
    }
}
