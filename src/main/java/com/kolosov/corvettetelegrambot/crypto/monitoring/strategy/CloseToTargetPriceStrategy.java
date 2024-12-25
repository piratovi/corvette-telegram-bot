package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CloseToTargetPriceStrategy extends AbstractNotificationStrategy{

    private Double targetPrice;

    @Override
    public boolean condition() {
        return calculatePriceDiff(quote.price()) < 5;
    }

    @Override
    public String prepareNotificationMessage() {
        double diff = calculatePriceDiff(quote.price());
        return  """
                Close to target price = %f.
                Diff = %f %%
                %s""".formatted(targetPrice, diff, quote);
    }

    private double calculatePriceDiff(double quotePrice) {
        return Math.abs((targetPrice / quotePrice) - 1) * 100;
    }

}
