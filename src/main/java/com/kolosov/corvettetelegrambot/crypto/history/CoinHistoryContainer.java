package com.kolosov.corvettetelegrambot.crypto.history;

import java.util.List;

public record CoinHistoryContainer(
        List<CoinHistory> dailyHistory,
        List<CoinHistory> hourlyHistory,
        List<CoinHistory> minutesHistory
) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recent ").append(dailyHistory.size()).append(" days:\n");
        dailyHistory().forEach(coinHistory -> sb.append(coinHistory.toString()).append("\n"));
        sb.append("Recent ").append(hourlyHistory.size()).append(" hours:\n");
        hourlyHistory().forEach(coinHistory -> sb.append(coinHistory.toString()).append("\n"));
        sb.append("Recent ").append(minutesHistory.size()).append(" minutes:\n");
        minutesHistory().forEach(coinHistory -> sb.append(coinHistory.toString()).append("\n"));
        return sb.toString();
    }
}
