package ru.tinkoff.edu.java.scrapper.constant;

public enum GitHubEventType {
    CREATE_EVENT("CreateEvent", "A Git branch or tag is created"),
    DELETE_EVENT("DeleteEvent", "A Git branch or tag is deleted"),
    FORK_EVENT("ForkEvent", "A user forks a repository"),
    PULL_REQUEST_EVENT("PullRequestEvent", "Pull requests added or changed"),
    PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent", "Pull requests review added or changed"),
    PUSH_EVENT("PushEvent", "One or more commits are pushed to a repository branch or tag");

    private final String description;
    private final String name;

    GitHubEventType(final String name, final String description) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
