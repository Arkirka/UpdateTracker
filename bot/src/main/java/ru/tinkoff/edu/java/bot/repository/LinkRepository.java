package ru.tinkoff.edu.java.bot.repository;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.entity.Link;

import java.util.List;

@Component
public class LinkRepository {
    public final List<Link> linkRepository;

    public LinkRepository(List<Link> linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> getAll() {
        return linkRepository;
    }

    public List<Link> getAllByChatId(Long chatId) {
        return linkRepository.stream()
                .filter(x -> x.getChat().getId().equals(chatId))
                .toList();
    }
}
