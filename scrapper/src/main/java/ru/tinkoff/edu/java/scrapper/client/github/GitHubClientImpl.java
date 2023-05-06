package ru.tinkoff.edu.java.scrapper.client.github;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.github.GitHubEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GitHubClientImpl implements GitHubClient {
    private static final String DEFAULT_URL = "https://api.github.com";
    private final WebClient webClient;

    public GitHubClientImpl(WebClient.Builder webClientBuilder, String baseUrl) {
        String url = baseUrl == null || baseUrl.isBlank() ? DEFAULT_URL : baseUrl;
        this.webClient = webClientBuilder.baseUrl(url).build();
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

    @Override
    public List<GitHubEvent> fetchRepositoryEventsBeforeId(String owner, String repo, String id) {
        if (id == null)
            return fetchAllRepositoryEvents(owner, repo);

        final int countOfEventsPerPage = 10;
        int currentPage = 1;
        boolean found = false;
        List<GitHubEvent> allEvents = new ArrayList<>();

        while (!found) {
            GitHubEvent[] events = webClient.get()
                    .uri(
                            "/repos/{owner}/{repo}/events?per_page={countOfEventsPerPage}&page={currentPage}",
                            owner, repo, countOfEventsPerPage, currentPage
                    )
                    .retrieve()
                    .bodyToMono(GitHubEvent[].class)
                    .block();

            if (events != null && events.length > 0) {
                for (GitHubEvent event : events) {
                    if (event.getId().equals(id)) {
                        found = true;
                        break;
                    }
                    allEvents.add(event);
                }

                if (!found) {
                    currentPage++;
                }
            } else
                break;
        }

        return allEvents;
    }

    @Override
    public List<GitHubEvent> fetchAllRepositoryEvents(String owner, String repo) {
        final int countOfEventsPerPage = 10;
        int currentPage = 1;
        List<GitHubEvent> allEvents = new ArrayList<>();

        while (true) {
            GitHubEvent[] events = webClient.get()
                    .uri(
                            "/repos/{owner}/{repo}/events?per_page={countOfEventsPerPage}&page={currentPage}",
                            owner, repo, countOfEventsPerPage, currentPage
                    )
                    .retrieve()
                    .bodyToMono(GitHubEvent[].class)
                    .block();

            if (events != null && events.length > 0) {
                allEvents.addAll(Arrays.asList(events));
                currentPage++;
            } else {
                break;
            }
        }

        return allEvents;
    }
}
