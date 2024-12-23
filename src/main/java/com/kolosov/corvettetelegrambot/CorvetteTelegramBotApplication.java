package com.kolosov.corvettetelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CorvetteTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorvetteTelegramBotApplication.class, args);
    }

}
