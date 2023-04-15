package ru.tinkoff.edu.java.scrapper.dto.github;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class GitHubRepositoryResponse {
    private String name;
    private String full_name;
    private String description;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
}
