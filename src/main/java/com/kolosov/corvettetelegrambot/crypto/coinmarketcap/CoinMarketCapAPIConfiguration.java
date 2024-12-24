package com.kolosov.corvettetelegrambot.crypto.coinmarketcap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CoinMarketCapAPIConfiguration {

    @Bean
    public CoinMarketCapAPI coinMarketCapAPI(
            RestClient.Builder restClientBuilder,
            @Value("${COINMARKETCAP_API_KEY}") String coinmarketcapApiKey
    ) {
        RestClient restClient = restClientBuilder.clone()
                .defaultHeader("X-CMC_PRO_API_KEY", coinmarketcapApiKey)
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(CoinMarketCapAPI.class);
    }
}
