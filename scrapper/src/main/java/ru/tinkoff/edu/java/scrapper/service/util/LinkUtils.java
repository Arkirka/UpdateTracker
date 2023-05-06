package ru.tinkoff.edu.java.scrapper.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;

import java.net.URI;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class LinkUtils {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    public String getLastUpdatedId(LinkType linkType, URI url){
        if (linkType == LinkType.GITHUB){
            var ownerAndRepo = new Parser(url.getPath()).parse().get().split("/");
            var events =  gitHubClient.fetchLastRepositoryEvent(ownerAndRepo[0], ownerAndRepo[1]);

            return events == null || events.length == 0
                ? null
                : events[0].getId();
        }
        if (linkType == LinkType.STACKOVERFLOW){
            var questionId = new Parser(url.getPath()).parse().get();
            var events = stackOverflowClient.fetchLastQuestionAnswer(Long.parseLong(questionId)).getAnswers();

            return events == null || events.size() == 0
                ? null
                : String.valueOf(events.get(0).getAnswerId());
        }
        return null;
    }

    public LinkType getLinkType(URI url) {
        Predicate<String> isKnownHost = x -> url.getHost().contains(x);
        if (new Parser(url.getPath()).parse().isPresent()){
            if (isKnownHost.test("github.com"))
                return LinkType.GITHUB;
            if (isKnownHost.test("stackoverflow.com"))
                return LinkType.STACKOVERFLOW;
        }
        return LinkType.UNKNOWN;
    }
}
