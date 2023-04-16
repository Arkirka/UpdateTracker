package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.dao.ChatDao;
import ru.tinkoff.edu.java.scrapper.dao.LinkDao;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class JdbcLinkService implements LinkService {
    private final LinkDao linkDao;
    private final ChatDao chatDao;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    @Override
    public Optional<Link> add(long tgChatId, URI url) {
        if (!chatDao.exists(tgChatId))
            return Optional.empty();
        Link link = new Link();
        link.setLink(url.getPath());
        link.setChatId(tgChatId);
        link.setLinkType(getLinkType(url));
        link.setLastUpdatedId(getLastUpdatedId(link.getLinkType(), url));
        return Optional.of(linkDao.add(link));
    }

    private String getLastUpdatedId(LinkType linkType, URI url){
        if (linkType == LinkType.GITHUB){
            var ownerAndRepo = new Parser(url.getPath()).parse().get().split("/");
            var events =  gitHubClient.fetchLastRepositoryEvent(ownerAndRepo[0], ownerAndRepo[1]);
            if (events == null || events.length == 0)
                return null;
            return events[0].getId();
        }
        if (linkType == LinkType.STACKOVERFLOW){
            var questionId = new Parser(url.getPath()).parse().get();
            var events = stackOverflowClient.fetchLastQuestionAnswer(Long.parseLong(questionId)).getAnswers();
            if (events == null || events.size() == 0)
                return null;
            return String.valueOf(events.get(0).getAnswerId());
        }
        return null;
    }

    private LinkType getLinkType(URI url) {
        Predicate<String> isKnownHost = x -> url.getHost().contains(x);
        if (new Parser(url.getPath()).parse().isPresent()){
            if (isKnownHost.test("github.com"))
                return LinkType.GITHUB;
            if (isKnownHost.test("stackoverflow.com"))
                return LinkType.STACKOVERFLOW;
        }
        return LinkType.UNKNOWN;
    }

    @Override
    public Optional<Link> update(Link link) {
        return Optional.of(linkDao.update(link));
    }

    @Override
    public Optional<Link> remove(long tgChatId, URI url) {
        return Optional.of(linkDao.findByChatIdAndLink(tgChatId, url.getPath()));
    }

    @Override
    public List<Link> findAllByTgChatId(long tgChatId) {
        return linkDao.findAllByTgChatId(tgChatId);
    }

    @Override
    public List<Link> findAllOldByLinkType(LinkType linkType) {
        return linkDao.findAllOldByLinkType(linkType);
    }
}
