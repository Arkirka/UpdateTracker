package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLinkRequest {
    @NotBlank
    private String link;
}
