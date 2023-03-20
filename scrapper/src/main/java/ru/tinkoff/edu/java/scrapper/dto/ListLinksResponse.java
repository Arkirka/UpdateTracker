package ru.tinkoff.edu.java.scrapper.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class ListLinksResponse {
    @NotNull
    private List<LinkResponse> links;
    @NotNull
    @Positive
    private Integer size;

    public List<LinkResponse> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResponse> links) {
        this.links = links;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
