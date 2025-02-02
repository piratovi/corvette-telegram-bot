package com.kolosov.corvettetelegrambot.config;

import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class DynamoDBConfig extends BaseDynamoDBConfig {

    @Bean
    public DynamoDbTable<CryptoOrder> cryptoOrderTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table("crypto-orders",
                TableSchema.fromBean(CryptoOrder.class));
    }
} 