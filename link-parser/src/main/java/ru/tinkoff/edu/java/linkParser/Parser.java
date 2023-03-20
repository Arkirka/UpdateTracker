package ru.tinkoff.edu.java.linkParser;

import ru.tinkoff.edu.java.linkParser.handler.AbstractLinkHandler;
import ru.tinkoff.edu.java.linkParser.handler.GitHubLinkHandler;
import ru.tinkoff.edu.java.linkParser.handler.StackOverflowLinkHandler;

public record Parser(String link) {
    public String parse() {
        AbstractLinkHandler handler = getHandlerChain();
        return handler.handle(link);
    }

    private AbstractLinkHandler getHandlerChain(){
        return new GitHubLinkHandler(new StackOverflowLinkHandler(null));
    }
}
