package com.kolosov.corvettetelegrambot.crypto.history.mapper;

import com.kolosov.corvettetelegrambot.crypto.cryptocompare.dto.CryptoData;
import com.kolosov.corvettetelegrambot.crypto.history.CoinHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CryptoHistoryMapper {

    List<CoinHistory> map(List<CryptoData> cryptoData);

    @Mapping(target = "timeUnit", source = "unit")
    @Mapping(target = "localDateTime", source = "timestamp", qualifiedByName = "toLocalDateTime")
    @Mapping(target = "price", source = "close", qualifiedByName = "round")
    CoinHistory map(CryptoData cryptoData);

    @Named("toLocalDateTime")
    default LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.of("UTC")
        );
    }

    @Named("round")
    default double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
