package com.kolosov.corvettetelegrambot.crypto.history;

import com.kolosov.corvettetelegrambot.crypto.Cryptocurrency;
import com.kolosov.corvettetelegrambot.crypto.cryptocompare.CryptocompareAPI;
import com.kolosov.corvettetelegrambot.crypto.cryptocompare.dto.CryptoData;
import com.kolosov.corvettetelegrambot.crypto.cryptocompare.dto.CryptocompareResponse;
import com.kolosov.corvettetelegrambot.crypto.history.mapper.CryptoHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CryptoHistoryService {

    private final CryptocompareAPI cryptocompareAPI;
    private final CryptoHistoryMapper mapper;

    public CoinHistoryContainer getHistory(Cryptocurrency coinName) {
        List<CryptoData> dailyHistory = getHistory(coinName, "days", 30);
        List<CryptoData> hourlyHistory = getHistory(coinName, "hours", 24);
        List<CryptoData> minutesHistory = getHistory(coinName, "minutes", 30);
        return new CoinHistoryContainer(
                mapper.map(dailyHistory),
                mapper.map(hourlyHistory),
                mapper.map(minutesHistory)
        );
    }

    @NotNull
    private List<CryptoData> getHistory(Cryptocurrency coinName, String timeUnit, int limit) {
        Map<String, Object> requestParams = Map.of(
                "market", "cadli",
                "instrument", coinName + "-USD",
                "limit", limit,
                "groups", List.of("OHLC")
        );
        CryptocompareResponse dailyHistoryResponse = cryptocompareAPI.getDaysHistory(timeUnit, requestParams);
        return new ArrayList<>(Arrays.asList(dailyHistoryResponse.data()));
    }
}
