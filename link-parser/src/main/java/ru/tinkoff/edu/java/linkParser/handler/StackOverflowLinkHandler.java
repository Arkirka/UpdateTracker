package ru.tinkoff.edu.java.linkParser.handler;

import ru.tinkoff.edu.java.linkParser.handler.util.LinkDelimiter;

public final class StackOverflowLinkHandler extends AbstractLinkHandler {
    public StackOverflowLinkHandler(AbstractLinkHandler handler) {
        super(handler);
    }

    @Override
    public String handle(String link) {
        if (link == null || link.isBlank())
            return null;
        var linkArray = LinkDelimiter.splitLink(link);
        boolean sizeCondition = linkArray.length < 3;
        boolean keyWordsCondition = !linkArray[0].contains("stackoverflow.com") || !linkArray[1].equals("questions");
        if (sizeCondition || keyWordsCondition)
            return next == null ? null : next.handle(link);
        return linkArray[2];
    }
}
