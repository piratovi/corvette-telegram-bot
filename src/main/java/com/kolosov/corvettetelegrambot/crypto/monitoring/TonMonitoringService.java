package com.kolosov.corvettetelegrambot.crypto.monitoring;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.TonService;
import com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto.UsdQuote;
import com.kolosov.corvettetelegrambot.crypto.monitoring.strategy.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO refactor
@Service
@RequiredArgsConstructor
public class TonMonitoringService {

    private static final int EVERY_10_MINUTES = 10 * 60 * 1_000;
    private final TonService tonService;
    private final PersonalTelegramClient personalTelegramClient;

    @Value("${SELLING_PRICE}")
    private Double sellingPrice;

    @Value("${BUYING_PRICE}")
    private Double buyingPrice;

    private List<AbstractNotificationStrategy> strategies;

    @PostConstruct
    public void init() {
        strategies = List.of(

                HourlyBigChangeStrategy.builder()
                        .personalTelegramClient(personalTelegramClient)
                        .build(),

                DailyBigChangeStrategy.builder()
                        .personalTelegramClient(personalTelegramClient)
                        .build(),

                CloseToTargetPriceStrategy.builder()
                        .personalTelegramClient(personalTelegramClient)
                        .targetPrice(sellingPrice)
                        .build(),

                CloseToTargetPriceStrategy.builder()
                        .personalTelegramClient(personalTelegramClient)
                        .targetPrice(buyingPrice)
                        .build(),

                ReachTargetPriceStrategy.builder()
                        .personalTelegramClient(personalTelegramClient)
                        .targetPrice(sellingPrice)
                        .build(),

                ReachTargetPriceStrategy.builder()
                        .personalTelegramClient(personalTelegramClient)
                        .targetPrice(buyingPrice)
                        .build()
        );
    }

    @Scheduled(fixedRate = EVERY_10_MINUTES)
    public void monitorTon() {
        UsdQuote quote = tonService.getTonQuote();
        strategies.forEach(strategy -> strategy.execute(quote));
    }

}
