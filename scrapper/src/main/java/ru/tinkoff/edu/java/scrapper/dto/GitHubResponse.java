package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class GitHubResponse {
    private String name;
    private String full_name;
    private String description;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
}
