package ru.tinkoff.edu.java.scrapper.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StackOverflowAnswerResponse {
    @JsonProperty("items")
    private List<Answer> answers;
    @JsonProperty("has_more")
    private boolean hasMore;
}