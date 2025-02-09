package com.kolosov.corvettetelegrambot.repository;

import com.kolosov.corvettetelegrambot.bot.BotState;
import com.kolosov.corvettetelegrambot.bot.dto.BotStateType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BotStateRepository {

    private final DynamoDbTable<BotState> table;

    private static final String BOT_STATE_PK = "BOT_STATE";

    public void setBotState(BotStateType stateType) {
        var state = new BotState();
        state.setId(BOT_STATE_PK);
        state.setState(stateType);
        state.setCreatedAt(ZonedDateTime.now());
        table.putItem(state);

    }

    public BotStateType getBotState() {
        Key key = Key.builder()
                .partitionValue(BOT_STATE_PK)
                .build();
        return table.query(r -> {
            r.queryConditional(QueryConditional.keyEqualTo(key))
                    .scanIndexForward(false)
                    .limit(1);
        })
                .items()
                .stream()
                .findFirst()
                .map(BotState::getState)
                .orElse(BotStateType.IDLE);

    }
}