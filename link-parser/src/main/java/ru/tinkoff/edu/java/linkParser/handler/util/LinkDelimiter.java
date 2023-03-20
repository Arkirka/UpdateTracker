package ru.tinkoff.edu.java.linkParser.handler.util;

public final class LinkDelimiter {
    public static String[] splitLink(String link) {
        int protocolSlashes = 3;
        return link.substring(link.indexOf(':') + protocolSlashes).split("/");
    }
}
