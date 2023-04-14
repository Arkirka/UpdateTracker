package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JdbcLinkService implements LinkService {
    private final LinkDao linkDao;
    private final ChatDao chatDao;

    @Override
    public Optional<Link> add(long tgChatId, URI url) {
        if (!chatDao.exists(tgChatId))
            return Optional.empty();
        Link link = new Link();
        link.setLink(url.getPath());
        link.setChatId(tgChatId);
        return Optional.of(linkDao.add(link));
    }

    @Override
    public Optional<Link> remove(long tgChatId, URI url) {
        return Optional.of(linkDao.findByChatIdAndLink(tgChatId, url.getPath()));
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return linkDao.findAll();
    }
}
