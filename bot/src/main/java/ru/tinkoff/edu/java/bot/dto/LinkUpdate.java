package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LinkUpdate {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String url;
    private String description;
    @NotEmpty
    private List<Long> tgChatIds;
}
