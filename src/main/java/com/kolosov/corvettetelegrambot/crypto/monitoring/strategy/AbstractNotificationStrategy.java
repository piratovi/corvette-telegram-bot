package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class AbstractNotificationStrategy {

    private final PersonalTelegramClient personalTelegramClient;
    protected final UsdQuote quote;

    public void execute() {
        if (condition()) {
            String message = prepareNotificationMessage();
            personalTelegramClient.execute(message);
        }
    }

    public abstract boolean condition();

    public abstract String prepareNotificationMessage();
}
