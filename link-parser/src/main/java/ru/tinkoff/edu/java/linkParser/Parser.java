package ru.tinkoff.edu.java.linkParser;

import ru.tinkoff.edu.java.linkParser.handler.AbstractLinkHandler;
import ru.tinkoff.edu.java.linkParser.handler.GitHubLinkHandler;
import ru.tinkoff.edu.java.linkParser.handler.StackOverflowLinkHandler;

import java.util.Optional;

public record Parser(String link) {
    public Optional<String> parse() {
        AbstractLinkHandler handler = getHandlerChain();
        return Optional.ofNullable(handler.handle(link));
    }

    private AbstractLinkHandler getHandlerChain(){
        return new GitHubLinkHandler(new StackOverflowLinkHandler(null));
    }
}
