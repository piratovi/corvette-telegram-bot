package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SuperBuilder
public abstract class AbstractNotificationStrategy {

    private final PersonalTelegramClient personalTelegramClient;
    protected final UsdQuote quote;
    protected LocalDateTime lastNotificationDate;

    public void execute() {
        if (condition()) {
            String message = prepareNotificationMessage();
            personalTelegramClient.execute(message);
            lastNotificationDate = LocalDateTime.now();
        }
    }

    public abstract boolean condition();

    protected boolean hasTimePastSinceLastNotification(ChronoUnit chronoUnit, int amount) {
        if (lastNotificationDate == null) {
            return true;
        }
        long daysSinceLastNotification = chronoUnit.between(lastNotificationDate, LocalDateTime.now());
        return daysSinceLastNotification >= amount;
    }

    public abstract String prepareNotificationMessage();
}
