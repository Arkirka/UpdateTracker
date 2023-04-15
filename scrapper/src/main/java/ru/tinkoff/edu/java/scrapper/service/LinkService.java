package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.model.Link;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface LinkService {
    Optional<Link> add(long tgChatId, URI url);
    Optional<Link> update(Link link);
    Optional<Link> remove(long tgChatId, URI url);
    List<Link> findAllByTgChatId(long tgChatId);
    List<Link> findAllOldByLinkType(LinkType linkType);
}
