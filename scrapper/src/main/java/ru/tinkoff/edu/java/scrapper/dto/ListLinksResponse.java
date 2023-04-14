package ru.tinkoff.edu.java.scrapper.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListLinksResponse {
    @NotNull
    private List<LinkResponse> links;
    @NotNull
    @Positive
    private Integer size;
}
