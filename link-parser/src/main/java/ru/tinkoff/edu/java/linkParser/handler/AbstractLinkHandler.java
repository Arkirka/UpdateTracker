package ru.tinkoff.edu.java.linkParser.handler;

public abstract sealed class AbstractLinkHandler permits GitHubLinkHandler, StackOverflowLinkHandler {
    public AbstractLinkHandler next;

    public AbstractLinkHandler(AbstractLinkHandler handler) {
        next = handler;
    }

    public abstract String handle(String link);
}
