package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class LinkResponse {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private URI url;
}
