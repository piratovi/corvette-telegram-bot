package com.kolosov.corvettetelegrambot.crypto.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public record Data(Map<String, CryptoCoin> coins) {
    public Data {
        coins = new HashMap<>();
    }

    @JsonAnySetter
    private void add(String key, CryptoCoin value) {
        coins.put(key, value);
    }
}
