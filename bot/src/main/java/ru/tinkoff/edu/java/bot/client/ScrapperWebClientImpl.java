package ru.tinkoff.edu.java.bot.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.RemoveLinkRequest;

public class ScrapperWebClientImpl implements ScrapperWebClient{
    private static final String DEFAULT_URL = "https://localhost:8082/scrapper";
    private final WebClient webClient;

    public ScrapperWebClientImpl(String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl;
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    // Я оставил Mono<> на методах чтобы в случае ошибки её можно было обработать в теле вызывающего метода,
    // а также в случае необходимости добавить ещё бизнес логики

    @Override
    public Mono<Void> registerChat(Long id) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/tg-chat/{id}").build(id))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Void> deleteChat(Long id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/tg-chat/{id}").build(id))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<ListLinksResponse> getAllLinks(Long tgChatId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/links").build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Tg-Chat-Id", tgChatId.toString())
                .retrieve()
                .bodyToMono(ListLinksResponse.class);
    }

    @Override
    public Mono<LinkResponse> addLink(Long tgChatId, AddLinkRequest linkRequest) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/links").build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Tg-Chat-Id", tgChatId.toString())
                .body(Mono.just(linkRequest), AddLinkRequest.class)
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }

    @Override
    public Mono<LinkResponse> removeLink(Long tgChatId, RemoveLinkRequest request) {
        return webClient.method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder.path("/links").build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Tg-Chat-Id", tgChatId.toString())
                .body(Mono.just(request), AddLinkRequest.class)
                .retrieve()
                .bodyToMono(LinkResponse.class);
    }
}
