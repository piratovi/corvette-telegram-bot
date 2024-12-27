package com.kolosov.corvettetelegrambot.crypto.cryptocompare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CryptoData(
        @JsonProperty("UNIT")
        String unit,

        @JsonProperty("TIMESTAMP")
        long timestamp,

        @JsonProperty("TYPE")
        String type,

        @JsonProperty("MARKET")
        String market,

        @JsonProperty("INSTRUMENT")
        String instrument,

        @JsonProperty("OPEN")
        double open,

        @JsonProperty("HIGH")
        double high,

        @JsonProperty("LOW")
        double low,

        @JsonProperty("CLOSE")
        double close,

        @JsonProperty("FIRST_MESSAGE_TIMESTAMP")
        long firstMessageTimestamp,

        @JsonProperty("LAST_MESSAGE_TIMESTAMP")
        long lastMessageTimestamp,

        @JsonProperty("FIRST_MESSAGE_VALUE")
        double firstMessageValue,

        @JsonProperty("HIGH_MESSAGE_VALUE")
        double highMessageValue,

        @JsonProperty("HIGH_MESSAGE_TIMESTAMP")
        long highMessageTimestamp,

        @JsonProperty("LOW_MESSAGE_VALUE")
        double lowMessageValue,

        @JsonProperty("LOW_MESSAGE_TIMESTAMP")
        long lowMessageTimestamp,

        @JsonProperty("LAST_MESSAGE_VALUE")
        double lastMessageValue,

        @JsonProperty("TOTAL_INDEX_UPDATES")
        int totalIndexUpdates,

        @JsonProperty("VOLUME")
        double volume,

        @JsonProperty("QUOTE_VOLUME")
        double quoteVolume,

        @JsonProperty("VOLUME_TOP_TIER")
        double volumeTopTier,

        @JsonProperty("QUOTE_VOLUME_TOP_TIER")
        double quoteVolumeTopTier,

        @JsonProperty("VOLUME_DIRECT")
        double volumeDirect,

        @JsonProperty("QUOTE_VOLUME_DIRECT")
        double quoteVolumeDirect,

        @JsonProperty("VOLUME_TOP_TIER_DIRECT")
        double volumeTopTierDirect,

        @JsonProperty("QUOTE_VOLUME_TOP_TIER_DIRECT")
        double quoteVolumeTopTierDirect
) {
}
