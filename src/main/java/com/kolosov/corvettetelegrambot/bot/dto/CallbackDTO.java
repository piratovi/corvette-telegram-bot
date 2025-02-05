package com.kolosov.corvettetelegrambot.bot.dto;

import java.util.Optional;

public record CallbackDTO(
    String command,
    Optional<String> subcommand,
    Optional<String> argument
) {} 