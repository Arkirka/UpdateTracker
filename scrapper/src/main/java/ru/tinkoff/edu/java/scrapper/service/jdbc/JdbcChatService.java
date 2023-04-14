package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RequiredArgsConstructor
@Service
public class JdbcChatService implements ChatService {
    private final ChatDao chatDao;

    @Override
    public void register(long tgChatId) {
        chatDao.add(new Chat(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }

    @Override
    public boolean exists(long tgChatId) {
        return chatDao.exists(tgChatId);
    }
}
