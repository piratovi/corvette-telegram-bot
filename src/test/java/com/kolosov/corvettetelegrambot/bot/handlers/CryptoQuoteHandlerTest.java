package com.kolosov.corvettetelegrambot.bot.handlers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import com.kolosov.corvettetelegrambot.PersonalTelegramClient;
import com.kolosov.corvettetelegrambot.crypto.CryptoQuoteService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CryptoQuoteHandlerTest {
    
    @Mock
    private CryptoQuoteService cryptoQuoteService;
    
    @Mock
    private PersonalTelegramClient personalTelegramClient;
    
    @InjectMocks
    private CryptoQuoteHandler cryptoQuoteHandler;
    
    @Test
    void getArgument_shouldReturnArgument_whenCommandHasArgument() {
        // Arrange
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        MessageEntity commandEntity = mock(MessageEntity.class);
        
        when(update.getMessage()).thenReturn(message);
        when(message.getEntities()).thenReturn(List.of(commandEntity));
        when(commandEntity.getLength()).thenReturn(7); // Length of "/crypto"
        when(message.getText()).thenReturn("/crypto BTC");
        
        // Act
        Optional<String> result = cryptoQuoteHandler.getArgument(update.getMessage());
        
        // Assert
        assertThat(result).isPresent()
                         .contains("BTC");
    }
    
    @Test
    void getArgument_shouldReturnEmpty_whenCommandHasNoArgument() {
        // Arrange
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        MessageEntity commandEntity = mock(MessageEntity.class);
        
        when(update.getMessage()).thenReturn(message);
        when(message.getEntities()).thenReturn(List.of(commandEntity));
        when(commandEntity.getLength()).thenReturn(6); // Length of "/crypto"
        when(message.getText()).thenReturn("/crypto");
        
        // Act
        Optional<String> result = cryptoQuoteHandler.getArgument(update.getMessage());
        
        // Assert
        assertThat(result).isEmpty();
    }
}
