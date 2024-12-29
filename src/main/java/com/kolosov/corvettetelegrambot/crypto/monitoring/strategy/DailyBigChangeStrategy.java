package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import lombok.experimental.SuperBuilder;

import static java.time.temporal.ChronoUnit.DAYS;

@SuperBuilder
public class DailyBigChangeStrategy extends AbstractNotificationStrategy{

    @Override
    public boolean condition() {
        double percentPriceChange = Math.abs(quote.percentChange24h());
        return percentPriceChange > 10 && hasTimePastSinceLastNotification(DAYS, 1);
    }

    @Override
    public String prepareNotificationMessage() {
        return """
                Big daily change. More than 10%%.
                %s""".formatted(quote);
    }
}
