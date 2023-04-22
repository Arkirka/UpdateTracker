package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface LinkService {
    Optional<LinkModel> add(long tgChatId, URI url);
    Optional<LinkModel> update(LinkModel link);
    Optional<LinkModel> remove(long tgChatId, URI url);
    List<LinkModel> findAllByTgChatId(long tgChatId);
    List<LinkModel> findAllOldByLinkType(LinkType linkType);
}
