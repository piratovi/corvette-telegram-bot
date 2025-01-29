package com.kolosov.corvettetelegrambot.config;

import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbClient ddb = DynamoDbClient.builder().build();
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }

    @Bean
    public DynamoDbTable<CryptoOrder> cryptoOrderTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(CryptoOrder.class.getSimpleName(),
                TableSchema.fromBean(CryptoOrder.class));
    }
} 