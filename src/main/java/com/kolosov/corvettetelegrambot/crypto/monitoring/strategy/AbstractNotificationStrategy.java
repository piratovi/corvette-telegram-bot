package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SuperBuilder
public abstract class AbstractNotificationStrategy {

    protected final Logger logger = LoggerFactory.getLogger(AbstractNotificationStrategy.class);

    private final PersonalTelegramClient personalTelegramClient;
    protected LocalDateTime lastNotificationDate;

    public void execute(UsdQuote quote) {
        if (condition(quote)) {
            String message = prepareNotificationMessage(quote);
            personalTelegramClient.send(message);
            lastNotificationDate = LocalDateTime.now();
            logger.info(message);
        }
    }

    public abstract boolean condition(UsdQuote quote);

    public abstract String prepareNotificationMessage(UsdQuote quote);

    protected boolean hasTimePastSinceLastNotification(ChronoUnit chronoUnit, int amount) {
        if (lastNotificationDate == null) {
            return true;
        }
        long daysSinceLastNotification = chronoUnit.between(lastNotificationDate, LocalDateTime.now());
        return daysSinceLastNotification >= amount;
    }
}
