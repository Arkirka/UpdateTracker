package ru.tinkoff.edu.java.scrapper.constant;

public enum AccessType {
    JDBC("jdbc"),
    JPA("jpa"),
    JOOQ("jooq")
    ;

    private final String text;

    AccessType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
