package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class LinkUpdateRequest {
    @NotNull
    @Positive
    private Long id;
    @NotBlank
    private String url;
    private String description;
    @NotNull
    private List<Long> tgChatIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getTgChatIds() {
        return tgChatIds;
    }

    public void setTgChatIds(List<Long> tgChatIds) {
        this.tgChatIds = tgChatIds;
    }
}
