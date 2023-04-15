package ru.tinkoff.edu.java.scrapper.dto.bot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdate {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String url;
    private String description;
    @NotNull
    private List<Long> tgChatIds;
}
