package ru.tinkoff.edu.java.linkParser.handler;

import java.net.MalformedURLException;
import java.net.URL;

public final class StackOverflowLinkHandler extends AbstractLinkHandler {
    public StackOverflowLinkHandler(AbstractLinkHandler handler) {
        super(handler);
    }

    @Override
    public String handle(String link) {
        String nextHandler = next == null ? null : next.handle(link);
        try {
            URL url = new URL(link);
            var pathArray = url.getPath().split("/");
            final int numPathParts = 3;
            boolean sizeCondition = pathArray.length < numPathParts;
            if (sizeCondition)
                return nextHandler;
            boolean hostCondition = !url.getHost().contains("stackoverflow.com");
            boolean keyWordsCondition = !pathArray[1].equals("questions");
            if (hostCondition || keyWordsCondition)
                return nextHandler;
            return pathArray[2];
        } catch (MalformedURLException e) {
            return nextHandler;
        }
    }
}
