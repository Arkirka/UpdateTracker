package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepository jdbcLinkRepository;
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
        return Optional.of(jdbcLinkRepository.add(link));
    }

    @Override
    public Optional<LinkModel> update(LinkModel link) {
        return Optional.of(jdbcLinkRepository.update(link));
    }

    @Override
    public Optional<LinkModel> remove(long tgChatId, URI url) {
        return Optional.of(jdbcLinkRepository.findByChatIdAndLink(tgChatId, url.getPath()));
    }

    @Override
    public List<LinkModel> findAllByTgChatId(long tgChatId) {
        return jdbcLinkRepository.findAllByTgChatId(tgChatId);
    }

    @Override
    public List<LinkModel> findAllOldByLinkType(LinkType linkType) {
        return jdbcLinkRepository.findAllOldByLinkType(linkType);
    }
}
