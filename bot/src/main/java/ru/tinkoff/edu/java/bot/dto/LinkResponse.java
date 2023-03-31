package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkResponse {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String url;
}
