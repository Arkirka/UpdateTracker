package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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
        link.setLinkType(getLinkType(url));
        return Optional.of(linkDao.add(link));
    }

    private LinkType getLinkType(URI url) {
        Predicate<String> isKnownHost = x -> url.getHost().contains(x);
        if (isKnownHost.test("github.com"))
            return LinkType.GITHUB;
        if (isKnownHost.test("stackoverflow.com"))
            return LinkType.STACKOVERFLOW;
        return LinkType.UNKNOWN;
    }

    @Override
    public Optional<Link> update(Link link) {
        return Optional.of(linkDao.update(link));
    }

    @Override
    public Optional<Link> remove(long tgChatId, URI url) {
        return Optional.of(linkDao.findByChatIdAndLink(tgChatId, url.getPath()));
    }

    @Override
    public List<Link> findAllByTgChatId(long tgChatId) {
        return linkDao.findAllByTgChatId(tgChatId);
    }

    @Override
    public List<Link> findAllOldByLinkType(LinkType linkType) {
        return linkDao.findAllOldByLinkType(linkType);
    }
}
