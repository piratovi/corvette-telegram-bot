package com.kolosov.corvettetelegrambot;

import com.kolosov.corvettetelegrambot.crypto.ai.LLMCostProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(LLMCostProperties.class)
public class CorvetteTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorvetteTelegramBotApplication.class, args);
    }

}
