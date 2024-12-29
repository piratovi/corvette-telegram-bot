package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import lombok.experimental.SuperBuilder;

import static java.time.temporal.ChronoUnit.DAYS;

@SuperBuilder
public class ReachTargetPriceStrategy extends AbstractNotificationStrategy {

    private double targetPrice;

    @Override
    public boolean condition() {
        double priceDiff = Math.abs(quote.price() - targetPrice);
        return priceDiff < 0.001
                && hasTimePastSinceLastNotification(DAYS, 1);
    }

    @Override
    public String prepareNotificationMessage() {
        return """
                Reached price = %f %%
                """.formatted(targetPrice);
    }

}
