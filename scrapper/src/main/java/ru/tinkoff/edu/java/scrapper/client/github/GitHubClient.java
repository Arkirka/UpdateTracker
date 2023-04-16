package ru.tinkoff.edu.java.scrapper.client.github;

import ru.tinkoff.edu.java.scrapper.dto.github.GitHubEvent;

import java.util.List;

public interface GitHubClient {
    GitHubEvent[] fetchLastRepositoryEvent(String owner, String repo);
    List<GitHubEvent> fetchRepositoryEventsBeforeId(String owner, String repo, String id);
    List<GitHubEvent> fetchAllRepositoryEvents(String owner, String repo);
}
