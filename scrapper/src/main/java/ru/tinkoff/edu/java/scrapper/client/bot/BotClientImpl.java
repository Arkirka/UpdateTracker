package ru.tinkoff.edu.java.scrapper.client.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.bot.LinkUpdate;

@Slf4j
public class BotClientImpl implements BotClient{
    private static final String DEFAULT_URL = "http://localhost:8090";
    private final WebClient webClient;

    public BotClientImpl(WebClient.Builder webClientBuilder, String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl;
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    public void sendNotification(LinkUpdate linkUpdate){
        this.webClient.post()
                .uri("updates")
                .header("Content-Type", "application/json")
                .body(Mono.just(linkUpdate), LinkUpdate.class)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        res -> {
                            res.toEntity(String.class).subscribe(
                                    entity -> log.warn("Send notification error {}", entity)
                            );
                            return Mono.error(new HttpClientErrorException(res.statusCode()));
                        }
                ).bodyToMono(String.class)
                .subscribe(response -> log.debug("Send notification {}", response));
    }
}
