package com.kolosov.corvettetelegrambot.repository;

import com.kolosov.corvettetelegrambot.configuration.DynamoDBConfig;
import com.kolosov.corvettetelegrambot.crypto.CryptoOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
    CryptoOrderRepository.class,
    DynamoDBConfig.class
})
@ActiveProfiles("dev")
class CryptoOrderRepositoryTest {

    @Autowired
    private CryptoOrderRepository repository;

    @AfterEach
    void cleanup() {
        // Delete all items from the table
        repository.findAll().forEach(repository::delete);
    }

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

    @Test
    void shouldFindAllOrders() {
        // given
        CryptoOrder order1 = new CryptoOrder();
        order1.setId(UUID.randomUUID().toString());
        order1.setCreatedAt(ZonedDateTime.now());
        order1.setCryptoCurrency("TON");
        order1.setAmount(BigDecimal.ONE);
        order1.setPrice(BigDecimal.valueOf(50000));

        CryptoOrder order2 = new CryptoOrder();
        order2.setId(UUID.randomUUID().toString());
        order2.setCreatedAt(ZonedDateTime.now());
        order2.setCryptoCurrency("BTC");
        order2.setAmount(BigDecimal.valueOf(2));
        order2.setPrice(BigDecimal.valueOf(60000));

        repository.save(order1);
        repository.save(order2);

        // when
        List<CryptoOrder> orders = repository.findAll();

        // then
        assertThat(orders).hasSize(2);
        assertThat(orders).extracting(CryptoOrder::getCryptoCurrency)
                .containsExactlyInAnyOrder("TON", "BTC");
    }

    @Test
    void shouldDeleteOrder() {
        // given
        CryptoOrder order = new CryptoOrder();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(ZonedDateTime.now());
        order.setCryptoCurrency("ETH");
        order.setAmount(BigDecimal.ONE);
        order.setPrice(BigDecimal.valueOf(3000));

        repository.save(order);
        assertThat(repository.findById(order.getId())).isPresent();

        // when
        repository.delete(order);

        // then
        assertThat(repository.findById(order.getId())).isEmpty();
    }
} 