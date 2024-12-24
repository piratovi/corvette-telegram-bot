package com.kolosov.corvettetelegrambot.crypto.coinmarketcap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record CryptoCoin(
        Long id,
        String name,
        String symbol,
        String slug,

        @JsonProperty("num_market_pairs")
        int numMarketPairs,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        @JsonProperty("date_added")
        Instant dateAdded,

        List<String> tags,

        @JsonProperty("max_supply")
        Double maxSupply,

        @JsonProperty("circulating_supply")
        double circulatingSupply,

        @JsonProperty("total_supply")
        double totalSupply,

        @JsonProperty("is_active")
        int isActive,

        @JsonProperty("infinite_supply")
        boolean infiniteSupply,

        Object platform,

        @JsonProperty("cmc_rank")
        int cmcRank,

        @JsonProperty("is_fiat")
        int isFiat,

        @JsonProperty("self_reported_circulating_supply")
        double selfReportedCirculatingSupply,

        @JsonProperty("self_reported_market_cap")
        double selfReportedMarketCap,

        @JsonProperty("tvl_ratio")
        Double tvlRatio,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        @JsonProperty("last_updated")
        Instant lastUpdated,

        Quote quote
) {}
