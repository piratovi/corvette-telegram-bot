package com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Quote(
        @JsonProperty("USD")
        UsdQuote usd
) {}
