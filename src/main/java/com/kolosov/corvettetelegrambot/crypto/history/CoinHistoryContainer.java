package com.kolosov.corvettetelegrambot.crypto.history;

import java.util.List;

public record CoinHistoryContainer(
        List<CoinHistory> dailyHistory,
        List<CoinHistory> hourlyHistory,
        List<CoinHistory> minutesHistory
) {
}
