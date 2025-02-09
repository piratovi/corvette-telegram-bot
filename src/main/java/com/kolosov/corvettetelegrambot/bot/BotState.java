package com.kolosov.corvettetelegrambot.bot;

import java.time.ZonedDateTime;

import com.kolosov.corvettetelegrambot.bot.dto.BotStateType;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDbBean
public class BotState {
    private String id;
    private BotStateType state;
    private ZonedDateTime createdAt;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @DynamoDbSortKey
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("""
                🤖 UUID: %s
                📊 State:    %s
                📅 Created:  %s""",
                id,
                state,
                createdAt.toLocalDateTime());

    }
} 