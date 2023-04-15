package ru.tinkoff.edu.java.scrapper.constant;

public enum LinkType {
    GITHUB("GITHUB"),
    STACKOVERFLOW("STACKOVERFLOW"),
    UNKNOWN("unknown");

    private final String text;

    LinkType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
