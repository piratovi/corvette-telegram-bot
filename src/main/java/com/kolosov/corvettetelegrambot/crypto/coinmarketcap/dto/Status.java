package com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record Status(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        Instant timestamp,

        @JsonProperty("error_code")
        int errorCode,

        @JsonProperty("error_message")
        String errorMessage,

        int elapsed,

        @JsonProperty("credit_count")
        int creditCount,

        String notice
) {
}
