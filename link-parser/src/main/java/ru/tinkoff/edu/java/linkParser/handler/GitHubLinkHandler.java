package ru.tinkoff.edu.java.linkParser.handler;

import ru.tinkoff.edu.java.linkParser.handler.util.LinkDelimiter;

public final class GitHubLinkHandler extends AbstractLinkHandler{
    public GitHubLinkHandler(AbstractLinkHandler handler) {
        super(handler);
    }

    @Override
    public String handle(String link) {
        if (link == null || link.isBlank())
            return null;
        var linkArray = LinkDelimiter.splitLink(link);
        boolean sizeCondition = linkArray.length < 3;
        boolean keyWordsCondition = !linkArray[0].contains("github.com");
        if (keyWordsCondition || sizeCondition)
            return next == null ? null : next.handle(link);
        return linkArray[1] + "/" + linkArray[2];
    }
}
