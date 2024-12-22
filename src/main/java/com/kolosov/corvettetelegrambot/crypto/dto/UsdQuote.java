package com.kolosov.corvettetelegrambot.crypto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record UsdQuote(
        double price,

        @JsonProperty("volume_24h")
        double volume24h,

        @JsonProperty("volume_change_24h")
        double volumeChange24h,

        @JsonProperty("percent_change_1h")
        double percentChange1h,

        @JsonProperty("percent_change_24h")
        double percentChange24h,

        @JsonProperty("percent_change_7d")
        double percentChange7d,

        @JsonProperty("percent_change_30d")
        double percentChange30d,

        @JsonProperty("percent_change_60d")
        double percentChange60d,

        @JsonProperty("percent_change_90d")
        double percentChange90d,

        @JsonProperty("market_cap")
        double marketCap,

        @JsonProperty("market_cap_dominance")
        double marketCapDominance,

        @JsonProperty("fully_diluted_market_cap")
        double fullyDilutedMarketCap,

        Double tvl,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        @JsonProperty("last_updated")
        Instant lastUpdated
) {}
