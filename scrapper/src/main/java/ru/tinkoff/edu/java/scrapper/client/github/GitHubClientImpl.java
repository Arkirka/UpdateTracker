package ru.tinkoff.edu.java.scrapper.client.github;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

public class GitHubClientImpl implements GitHubClient {
    private static final String DEFAULT_URL = "https://api.github.com";
    private final WebClient webClient;

    public GitHubClientImpl(WebClient.Builder webClientBuilder, String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl;
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    @Override
    public GitHubResponse fetchRepository(String owner, String repo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .bodyToMono(GitHubResponse.class)
                .block();
    }
}
