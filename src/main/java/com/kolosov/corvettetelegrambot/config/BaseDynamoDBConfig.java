package com.kolosov.corvettetelegrambot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class BaseDynamoDBConfig {

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbClient ddb = DynamoDbClient.builder().build();
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }
} 