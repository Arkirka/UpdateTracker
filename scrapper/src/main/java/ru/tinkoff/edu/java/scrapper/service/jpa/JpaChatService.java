package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@Service
@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;
    @Override
    public void register(long tgChatId) {
        jpaChatRepository.save(new Chat(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        jpaChatRepository.deleteById(tgChatId);
    }

    @Override
    public boolean exists(long tgChatId) {
        return jpaChatRepository.existsById(tgChatId);
    }
}
