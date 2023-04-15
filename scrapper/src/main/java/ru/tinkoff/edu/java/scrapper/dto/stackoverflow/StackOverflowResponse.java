package ru.tinkoff.edu.java.scrapper.dto.stackoverflow;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class StackOverflowResponse {
    private String title;
    private String link;
    private String body;
    private OffsetDateTime creation_date;
    private OffsetDateTime last_activity_date;
}