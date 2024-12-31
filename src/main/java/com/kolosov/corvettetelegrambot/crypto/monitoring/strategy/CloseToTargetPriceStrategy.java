package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.experimental.SuperBuilder;

import static java.time.temporal.ChronoUnit.DAYS;

@SuperBuilder
public class CloseToTargetPriceStrategy extends AbstractNotificationStrategy{

    private Double targetPrice;

    @Override
    public boolean condition(UsdQuote quote) {
        double percentPriceDiff = calculatePriceDiff(quote.price());
        return percentPriceDiff < 5 && hasTimePastSinceLastNotification(DAYS, 1);
    }

    @Override
    public String prepareNotificationMessage(UsdQuote quote) {
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
