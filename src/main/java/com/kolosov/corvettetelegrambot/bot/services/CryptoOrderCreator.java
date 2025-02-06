package com.kolosov.corvettetelegrambot.bot.services;

import org.springframework.stereotype.Service;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.repository.CryptoOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CryptoOrderCreator {

    private final CryptoOrderRepository cryptoOrderRepository;
    private final PersonalTelegramClient personalTelegramClient;

    public void startCreation() {

    }
}