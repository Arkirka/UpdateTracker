package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.model.ChatModel;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public void register(long tgChatId) {
        jdbcChatRepository.add(new ChatModel(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        jdbcChatRepository.remove(tgChatId);
    }

    @Override
    public boolean exists(long tgChatId) {
        return jdbcChatRepository.exists(tgChatId);
    }
}
