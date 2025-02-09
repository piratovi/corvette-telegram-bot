package com.kolosov.corvettetelegrambot.repository;

import static com.kolosov.corvettetelegrambot.crypto.OrderStatus.OPEN;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Service
@RequiredArgsConstructor
public class CryptoOrderRepository {

    private final DynamoDbTable<CryptoOrder> table;

    public List<CryptoOrder> findAll() {
        return table.scan(r -> r
                .filterExpression(Expression.builder()
                        .expression("#s = :status")
                        .expressionNames(Map.of("#s", "status"))
                        .expressionValues(Map.of(":status", AttributeValue.builder().s(OPEN.toString()).build()))
                        .build()))
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    public Optional<CryptoOrder> findById(String id) {
        return Optional.ofNullable(table.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    public void save(CryptoOrder order) {
        table.putItem(order);
    }

    public void delete(CryptoOrder order) {
        table.deleteItem(order);
    }

    public void deleteById(String id) {
        table.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }

}
