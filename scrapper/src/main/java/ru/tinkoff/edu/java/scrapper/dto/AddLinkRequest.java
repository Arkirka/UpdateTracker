package ru.tinkoff.edu.java.scrapper.dto;

import jakarta.validation.constraints.NotBlank;

public class AddLinkRequest {
    @NotBlank
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
