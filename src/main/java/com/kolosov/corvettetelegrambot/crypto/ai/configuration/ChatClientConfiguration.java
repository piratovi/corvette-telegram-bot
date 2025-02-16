package com.kolosov.corvettetelegrambot.crypto.ai.configuration;

import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient bedrockChatClient(BedrockProxyChatModel bedrockProxyChatModel) {
        return ChatClient.builder(bedrockProxyChatModel).build();
    }

    @Bean
    public ChatClient deepseekChatClient(
            @Value("${spring.ai.deepseek.api-key}") String apiKey,
            @Value("${spring.ai.deepseek.base-url}") String baseUrl) {
        
        OpenAiApi api = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();

        OpenAiChatModel deepseekModel = OpenAiChatModel.builder()
                .openAiApi(api)
                .build();

        return ChatClient.builder(deepseekModel)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-reasoner")
                        .temperature(0.2)
                        .build())
                .build();
    }
}
