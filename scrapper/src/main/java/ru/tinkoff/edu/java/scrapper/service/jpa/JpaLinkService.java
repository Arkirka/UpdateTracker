package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

import java.net.URI;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;
    private final LinkUtils linkUtils;

    @Override
    public Optional<LinkModel> add(long tgChatId, URI url) {
        var chat = jpaChatRepository.findById(tgChatId);
        if (chat.isEmpty())
            return Optional.empty();

        Link link = new Link();
        link.setChat(chat.get());
        link.setLink(url.getPath());
        link.setLinkType(linkUtils.getLinkType(url).toString());
        link.setLastUpdatedId(linkUtils.getLastUpdatedId(LinkType.valueOf(link.getLinkType()), url));

        link = jpaLinkRepository.save(link);
        return Optional.of(mapToModel(link));
    }

    @Override
    public Optional<LinkModel> update(LinkModel link) {
        var chat = jpaChatRepository.findById(link.getChatId());
        if (chat.isEmpty())
            return Optional.empty();

        Link jpaLink = new Link(link.getId(), link.getLink(), link.getLastUpdatedId(),
                link.getLastChecked(), link.getLinkType().toString(), chat.get());

        jpaLink = jpaLinkRepository.save(jpaLink);
        return Optional.of(mapToModel(jpaLink));
    }

    @Override
    public Optional<LinkModel> remove(long tgChatId, URI url) {
        var chat = jpaChatRepository.findById(tgChatId);
        if (chat.isEmpty())
            return Optional.empty();

        var link = jpaLinkRepository.findByChatAndLink(chat.get(), url.getPath());
        if (link.isEmpty())
            return Optional.empty();

        jpaLinkRepository.delete(link.get());
        return Optional.of(mapToModel(link.get()));
    }

    @Override
    public List<LinkModel> findAllByTgChatId(long tgChatId) {
        var chat = jpaChatRepository.findById(tgChatId);
        if (chat.isEmpty())
            return List.of();

        return jpaLinkRepository.findAllByChat(chat.get()).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkModel> findAllOldByLinkType(LinkType linkType) {
        return jpaLinkRepository
                .findAllByLinkTypeAndLastCheckedBefore(
                        new Timestamp(System.currentTimeMillis()), linkType.toString()
                ).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    private LinkModel mapToModel(Link link) {
        return new LinkModel(
                link.getId(),
                link.getLink(),
                link.getChat().getId(),
                link.getLastUpdatedId(),
                Date.valueOf(Objects.requireNonNull(link.getLastChecked()).toLocalDate()),
                LinkType.valueOf(link.getLinkType())
        );
    }
}
