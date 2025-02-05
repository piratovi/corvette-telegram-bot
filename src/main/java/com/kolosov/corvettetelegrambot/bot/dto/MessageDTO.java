package com.kolosov.corvettetelegrambot.bot.dto;

import java.util.Optional;

public record MessageDTO(
    String command,
    Optional<String> argument
) {} 