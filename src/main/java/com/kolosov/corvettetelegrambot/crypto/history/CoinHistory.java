package com.kolosov.corvettetelegrambot.crypto.history;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CoinHistory(
        TimeUnit timeUnit,
        LocalDateTime localDateTime,
        double price
) {
    public enum TimeUnit {
        DAY, HOUR, MINUTE
    }

    private static final DateTimeFormatter MINUTES_FORMATTER = DateTimeFormatter.ofPattern("dd.MM HH:mm");

    @Override
    public String toString() {
        return localDateTime.format(MINUTES_FORMATTER) + " : " + price;
    }
}
