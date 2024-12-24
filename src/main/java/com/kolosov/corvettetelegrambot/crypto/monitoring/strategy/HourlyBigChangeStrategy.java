package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class HourlyBigChangeStrategy extends AbstractNotificationStrategy{

    @Override
    public boolean condition() {
        return Math.abs(quote.percentChange1h()) > 5;
    }

    @Override
    public String prepareNotificationMessage() {
        return """
                Big hourly change. More than 5%%.
                %s""".formatted(quote);
    }
}
