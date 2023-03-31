package ru.tinkoff.edu.java.bot.client;

import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

public interface ScrapperWebClient {
    Mono<Void> registerChat(Long id);
    Mono<Void> deleteChat(Long id);
    Mono<ListLinksResponse> getAllLinks(Long tgChatId);
    Mono<LinkResponse> addLink(Long tgChatId, AddLinkRequest linkRequest);
    Mono<LinkResponse> removeLink(Long tgChatId, RemoveLinkRequest request);

}
