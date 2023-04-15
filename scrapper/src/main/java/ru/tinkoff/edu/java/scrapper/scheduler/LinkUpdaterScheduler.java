package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.dto.bot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterScheduler {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;
    private final LinkService linkService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}000")
    public void update() {
        log.info("Updating links...");
        checkGitHubLinks();
        checkStackOverflowLinks();
    }

    private void checkStackOverflowLinks() {
        List<Long> chatIds = new ArrayList<>();
        List<Link> linkList = linkService.findAllOldByLinkType(LinkType.STACKOVERFLOW);
        for (Link link : linkList){
            var questionId = new Parser(link.getLink()).parse();
            if (questionId.isEmpty())
                continue;
            // TODO: в следующем задании добавится новый запрос в stackoverflow на получение answers и их id
            // последний id ответа заменит эту строчку. Сейчас по заданию это не нужно
            String lastEventId = "someId";
            chatIds.add(link.getChatId());
            markLinkVerified(link, lastEventId);
        }
        botClient.sendNotification(
                new LinkUpdate(1L, "some", "just notification", chatIds)
        );
    }

    private void checkGitHubLinks(){
        List<Long> chatIds = new ArrayList<>();
        List<Link> linkList = linkService.findAllOldByLinkType(LinkType.GITHUB);
        for (Link link : linkList){
            var ownerAndRepo = new Parser(link.getLink()).parse();
            if (ownerAndRepo.isEmpty())
                continue;
            var ownerAndRepoArray = ownerAndRepo.get().split("/");
            var lastRepositoryEvent =
                    gitHubClient.fetchLastRepositoryEvent(
                            ownerAndRepoArray[0], ownerAndRepoArray[1]
                    );
            String lastEventId = lastRepositoryEvent[0].getId();
            if (lastEventId.equals(link.getLastUpdatedId()))
                continue;
            chatIds.add(link.getChatId());
            markLinkVerified(link, lastEventId);
        }
        botClient.sendNotification(
                new LinkUpdate(1L, "some", "just notification", chatIds)
        );
    }

    private void markLinkVerified(Link link, String lastUpdatedId){
        link.setLastUpdatedId(lastUpdatedId);
        link.setLastChecked(new Date(new java.util.Date(
                System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)
        ).getTime()));
        linkService.update(link);
    }
}
