package com.kolosov.corvettetelegrambot.configuration;

import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        DynamoDbClient ddb = DynamoDbClient.builder().build();
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }

    @Bean
    public DynamoDbTable<CryptoOrder> cryptoOrderTable(DynamoDbEnhancedClient enhancedClient) {
        String tablePrefix = isDevProfile() ? "dev-" : "";
        return enhancedClient.table(tablePrefix + "crypto-orders",
                TableSchema.fromBean(CryptoOrder.class));
    }

    private boolean isDevProfile() {
        for (String profile : environment.getActiveProfiles()) {
            if (profile.equals("dev")) {
                return true;
            }
        }
        return false;
    }
} 