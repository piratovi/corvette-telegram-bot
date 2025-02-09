package com.kolosov.corvettetelegrambot.crypto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

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

    @Override
    public String toString() {
        return String.format("""
                🔹 Order     #%s
                📅 Created:  %s
                💱 Currency: %s
                📊 Type:     %s
                📈 Status:   %s
                💰 Price:    %.2f
                📦 Amount:   %.1f""",
                id,
                createdAt.toLocalDateTime(),
                cryptoCurrency,
                type,
                status,
                price,
                amount);
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

}
