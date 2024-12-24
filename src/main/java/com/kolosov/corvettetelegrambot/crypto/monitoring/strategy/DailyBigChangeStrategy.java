package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class DailyBigChangeStrategy extends AbstractNotificationStrategy{

    @Override
    public boolean condition() {
        return Math.abs(quote.percentChange24h()) > 10;
    }

    @Override
    public String prepareNotificationMessage() {
        return """
                Big daily change. More than 10%%.
                %s""".formatted(quote);
    }
}
