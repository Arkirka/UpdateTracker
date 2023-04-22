package ru.tinkoff.edu.java.scrapper.client.github;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.github.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.github.GitHubRepositoryResponse;

public class GitHubClientImpl implements GitHubClient {
    private static final String DEFAULT_URL = "https://api.github.com";
    private final WebClient webClient;

    public GitHubClientImpl(WebClient.Builder webClientBuilder, String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl;
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    @Override
    public GitHubRepositoryResponse fetchRepository(String owner, String repo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GitHubRepositoryResponse.class)
                .block();
    }

    @Override
    public GitHubEvent[] fetchLastRepositoryEvent(String owner, String repo) {
        int countOfEvents = 1;
        return webClient.get()
                .uri("/repos/{owner}/{repo}/events?per_page={countOfEvents}", owner, repo, countOfEvents)
                .retrieve()
                .bodyToMono(GitHubEvent[].class)
                .block();
    }
}
