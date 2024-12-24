package com.kolosov.corvettetelegrambot.crypto.monitoring;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.TonService;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import com.kolosov.corvettetelegrambot.crypto.monitoring.strategy.CloseToTargetPriceStrategy;
import com.kolosov.corvettetelegrambot.crypto.monitoring.strategy.DailyBigChangeStrategy;
import com.kolosov.corvettetelegrambot.crypto.monitoring.strategy.HourlyBigChangeStrategy;
import com.kolosov.corvettetelegrambot.crypto.monitoring.strategy.ReachTargetPriceStrategy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TonMonitoringService {

    private static final int EVERY_30_MINUTES = 30 * 60 * 1_000;

    private final TonService tonService;
    private final PersonalTelegramClient personalTelegramClient;

    @Value("${SELLING_PRICE}")
    private Double sellingPrice;

    @Value("${BUYING_PRICE}")
    private Double buyingPrice;

    @SneakyThrows
    @Scheduled(fixedRate = EVERY_30_MINUTES)
    public void monitorTon() {
        UsdQuote quote = tonService.getTonQuote();

        HourlyBigChangeStrategy.builder()
                .personalTelegramClient(personalTelegramClient)
                .quote(quote)
                .build()
                .execute();

        DailyBigChangeStrategy.builder()
                .personalTelegramClient(personalTelegramClient)
                .quote(quote)
                .build()
                .execute();

        CloseToTargetPriceStrategy.builder()
                .personalTelegramClient(personalTelegramClient)
                .quote(quote)
                .targetPrice(sellingPrice)
                .build()
                .execute();

        CloseToTargetPriceStrategy.builder()
                .personalTelegramClient(personalTelegramClient)
                .quote(quote)
                .targetPrice(buyingPrice)
                .build()
                .execute();

        ReachTargetPriceStrategy.builder()
                .personalTelegramClient(personalTelegramClient)
                .quote(quote)
                .targetPrice(sellingPrice)
                .build()
                .execute();

        ReachTargetPriceStrategy.builder()
                .personalTelegramClient(personalTelegramClient)
                .quote(quote)
                .targetPrice(buyingPrice)
                .build()
                .execute();


    }

}
