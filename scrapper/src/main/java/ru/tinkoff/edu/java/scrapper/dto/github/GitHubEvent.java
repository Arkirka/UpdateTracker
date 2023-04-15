package ru.tinkoff.edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class GitHubEvent {
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("created_at")
    private Date created_at;
}
