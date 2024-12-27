package com.kolosov.corvettetelegrambot.crypto.cryptocompare;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@org.springframework.context.annotation.Configuration
public class CryptocompareAPIConfiguration {

    @Bean
    public CryptocompareAPI cryptocompareAPI(
            RestClient.Builder restClientBuilder,
            @Value("${CRYPTOCOMPARE_API_KEY}") String cryptocompareAPIApiKey
    ) {
        RestClient restClient = restClientBuilder.clone()
                .defaultHeader("Authorization", "Bearer " + cryptocompareAPIApiKey)
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(CryptocompareAPI.class);
    }
}
