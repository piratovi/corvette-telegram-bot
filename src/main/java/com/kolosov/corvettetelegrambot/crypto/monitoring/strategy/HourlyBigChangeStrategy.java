package com.kolosov.corvettetelegrambot.crypto.monitoring.strategy;

import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import lombok.experimental.SuperBuilder;

import static java.time.temporal.ChronoUnit.HOURS;

@SuperBuilder
public class HourlyBigChangeStrategy extends AbstractNotificationStrategy{

    @Override
    public boolean condition(UsdQuote quote) {
        double percentPriceChange = Math.abs(quote.percentChange1h());
        return percentPriceChange > 5 && hasTimePastSinceLastNotification(HOURS, 1);
    }

    @Override
    public String prepareNotificationMessage(UsdQuote quote) {
        return """
                Big hourly change. More than 5%%.
                %s""".formatted(quote);
    }
}
