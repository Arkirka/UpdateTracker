package ru.tinkoff.edu.java.scrapper.client.github;

import ru.tinkoff.edu.java.scrapper.dto.github.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.github.GitHubRepositoryResponse;

public interface GitHubClient {
    GitHubRepositoryResponse fetchRepository(String owner, String repo);
    GitHubEvent[] fetchLastRepositoryEvent(String owner, String repo);
}
