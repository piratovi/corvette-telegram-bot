package com.kolosov.corvettetelegrambot.crypto.cryptocompare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CryptocompareResponse(
        @JsonProperty("Data") CryptoData[] data,
        Object err
) {
}
