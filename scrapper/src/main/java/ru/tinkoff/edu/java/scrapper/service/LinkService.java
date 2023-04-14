package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.Link;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface LinkService {
    Optional<Link> add(long tgChatId, URI url);
    Optional<Link> remove(long tgChatId, URI url);
    List<Link> listAll(long tgChatId);
}
