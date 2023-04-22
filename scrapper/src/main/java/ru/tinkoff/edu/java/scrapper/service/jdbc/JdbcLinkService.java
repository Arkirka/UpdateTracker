package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.LinkDao;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JdbcLinkService implements LinkService {
    private final LinkDao linkDao;
    private final ChatService chatService;
    private final LinkUtils linkUtils;

    @Override
    public Optional<LinkModel> add(long tgChatId, URI url) {
        if (!chatService.exists(tgChatId))
            return Optional.empty();
        LinkModel link = new LinkModel();
        link.setLink(url.getPath());
        link.setChatId(tgChatId);
        link.setLinkType(linkUtils.getLinkType(url));
        link.setLastUpdatedId(linkUtils.getLastUpdatedId(link.getLinkType(), url));
        return Optional.of(linkDao.add(link));
    }

    @Override
    public Optional<LinkModel> update(LinkModel link) {
        return Optional.of(linkDao.update(link));
    }

    @Override
    public Optional<LinkModel> remove(long tgChatId, URI url) {
        return Optional.of(linkDao.findByChatIdAndLink(tgChatId, url.getPath()));
    }

    @Override
    public List<LinkModel> findAllByTgChatId(long tgChatId) {
        return linkDao.findAllByTgChatId(tgChatId);
    }

    @Override
    public List<LinkModel> findAllOldByLinkType(LinkType linkType) {
        return linkDao.findAllOldByLinkType(linkType);
    }
}
