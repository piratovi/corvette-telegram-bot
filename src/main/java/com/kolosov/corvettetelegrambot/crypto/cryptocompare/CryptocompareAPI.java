package com.kolosov.corvettetelegrambot.crypto.cryptocompare;

import com.kolosov.corvettetelegrambot.crypto.cryptocompare.dto.CryptocompareResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange(
        url = "https://data-api.cryptocompare.com",
        accept = "application/json"
)
public interface CryptocompareAPI {

    @GetExchange("/index/cc/v1/historical/{timeUnit}")
    CryptocompareResponse getDaysHistory(
            @PathVariable String timeUnit,
            @RequestParam Map<String, Object> requestParams);
}
