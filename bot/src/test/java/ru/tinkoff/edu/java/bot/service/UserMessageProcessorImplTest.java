package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserMessageProcessorImplTest {

    @Test
    void process_ShouldReturnSpecialMessage_WhenCommandNotExist() {
        UserMessageProcessor messageProcessor = new UserMessageProcessorImpl(null, null);

        SendMessage expected = new SendMessage(1, "Неизвестная команда");
        SendMessage source = messageProcessor.process(getMockUpdate());

        Assertions.assertEquals(expected.getParameters().get("text"), source.getParameters().get("text"));
    }

    private Update getMockUpdate() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        com.pengrad.telegrambot.model.Chat chat = Mockito.mock(com.pengrad.telegrambot.model.Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/some");
        return update;
    }
}