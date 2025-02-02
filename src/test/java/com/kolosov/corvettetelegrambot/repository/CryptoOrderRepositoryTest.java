package com.kolosov.corvettetelegrambot.repository;

import com.kolosov.corvettetelegrambot.config.DynamoDBConfig;
import com.kolosov.corvettetelegrambot.config.TestDynamoDBConfig;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
    CryptoOrderRepository.class,
    TestDynamoDBConfig.class
})
@ActiveProfiles("dev")
class CryptoOrderRepositoryTest {

    @Autowired
    private CryptoOrderRepository repository;

    @Test
    void shouldSaveAndRetrieveOrder() {
        // given
        CryptoOrder order = new CryptoOrder();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(ZonedDateTime.now());
        order.setCryptoCurrency("TON");
        order.setAmount(BigDecimal.ONE);
        order.setPrice(BigDecimal.valueOf(50000));

        // when
        repository.save(order);
        var found = repository.findById(order.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getCryptoCurrency()).isEqualTo("TON");
    }
} 