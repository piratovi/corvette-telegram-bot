package com.kolosov.corvettetelegrambot.repository;

import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoOrderRepository {
    
    private final DynamoDbTable<CryptoOrder> table;

    public List<CryptoOrder> findAll() {
        PageIterable<CryptoOrder> results = table.scan();
        return results.items().stream().collect(Collectors.toList());
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
}
