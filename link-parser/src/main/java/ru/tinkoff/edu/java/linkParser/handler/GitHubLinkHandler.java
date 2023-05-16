package ru.tinkoff.edu.java.linkParser.handler;

import java.net.MalformedURLException;
import java.net.URL;

public final class GitHubLinkHandler extends AbstractLinkHandler{
    public GitHubLinkHandler(AbstractLinkHandler handler) {
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
            boolean hostCondition = !url.getHost().contains("github.com");
            if (hostCondition || sizeCondition)
                return nextHandler;
            return pathArray[1] + "/" + pathArray[2];
        } catch (MalformedURLException e) {
            return nextHandler;
        }
    }
}
