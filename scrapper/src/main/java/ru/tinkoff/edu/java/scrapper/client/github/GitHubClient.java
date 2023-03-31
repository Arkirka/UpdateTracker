package ru.tinkoff.edu.java.scrapper.client.github;

import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

public interface GitHubClient {
    GitHubResponse fetchRepository(String owner, String repo);
}
