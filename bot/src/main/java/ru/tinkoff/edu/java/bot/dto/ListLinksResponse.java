package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListLinksResponse {
    @NotNull
    private List<LinkResponse> links;
    @NotNull
    @Positive
    private Integer size;
}
