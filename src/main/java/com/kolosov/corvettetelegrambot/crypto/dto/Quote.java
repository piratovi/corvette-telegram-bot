package com.kolosov.corvettetelegrambot.crypto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Quote(
        @JsonProperty("USD")
        UsdQuote usd
) {}
