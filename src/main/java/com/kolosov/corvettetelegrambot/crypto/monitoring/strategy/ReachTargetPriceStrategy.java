package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ReachTargetPriceStrategy extends AbstractNotificationStrategy {

    private double targetPrice;

    @Override
    public boolean condition() {
        return Math.abs(quote.price() - targetPrice) < 0.001;
    }

    @Override
    public String prepareNotificationMessage() {
        return """
                Reached price = %f %%
                """.formatted(targetPrice);
    }

}
