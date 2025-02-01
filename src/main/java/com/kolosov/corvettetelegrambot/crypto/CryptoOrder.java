package com.kolosov.corvettetelegrambot.crypto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class CryptoOrder {
    
    private String id;
    private ZonedDateTime createdAt;
    private String cryptoCurrency;
    private OrderType type;
    private OrderStatus status;
    private BigDecimal price;
    private BigDecimal amount;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
