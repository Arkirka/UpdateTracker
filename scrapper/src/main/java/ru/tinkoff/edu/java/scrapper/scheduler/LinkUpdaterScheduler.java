package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.constant.GitHubEventType;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.dto.bot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.notification.NotificationService;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterScheduler {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final NotificationService notificationService;
    private final LinkService linkService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}000")
    public void update() {
        log.info("Updating links...");
        checkGitHubLinks();
        checkStackOverflowLinks();
    }

    private void checkStackOverflowLinks() {
        HashMap<String, List<Long>> linkPerChatIds = new HashMap<>();
        List<LinkModel> linkList = linkService.findAllOldByLinkType(LinkType.STACKOVERFLOW);
        for (LinkModel link : linkList){
            var questionId = new Parser(link.getLink()).parse();
            if (questionId.isEmpty())
                continue;

            var response = stackOverflowClient.fetchLastQuestionAnswer(
                    Long.parseLong(questionId.get())
            );

            if (response == null || response.getAnswers() == null || response.getAnswers().isEmpty())
                continue;

            var answer = response.getAnswers().get(0);
            String lastAnswerId = answer.getAnswerId();

            if (lastAnswerId.equals(link.getLastUpdatedId()))
                continue;

            if (!linkPerChatIds.containsKey(link.getLink()))
                linkPerChatIds.put(link.getLink(), new ArrayList<>());

            linkPerChatIds.get(link.getLink()).add(link.getChatId());
            markLinkVerified(link, lastAnswerId);
        }
        final long[] index = {0};
        linkPerChatIds.forEach((link, chatIds) -> notificationService.send(
                new LinkUpdate(index[0]++, link, "There are new answers to the question!", chatIds)
        ));
    }

    private void checkGitHubLinks(){
        List<LinkModel> linkList = linkService.findAllOldByLinkType(LinkType.GITHUB);
        HashMap<String, List<Long>> linkAndDescriptionPerChatIds = new HashMap<>();

        for (LinkModel link : linkList){
            var ownerAndRepo = new Parser(link.getLink()).parse();
            if (ownerAndRepo.isEmpty())
                continue;

            var ownerAndRepoArray = ownerAndRepo.get().split("/");
            var repositoryEvents = gitHubClient.fetchRepositoryEventsBeforeId(
                ownerAndRepoArray[0], ownerAndRepoArray[1], link.getLastUpdatedId()
            );

            if (repositoryEvents == null || repositoryEvents.isEmpty())
                continue;

            String lastEventId = repositoryEvents.get(0).getId();
            if (lastEventId.equals(link.getLastUpdatedId()))
                continue;

            for (GitHubEventType type : GitHubEventType.values()) {
                String linkAndDescription = link.getLink() + ":::" + type.getDescription();
                repositoryEvents.stream()
                        .filter(event -> type.getName().equals(event.getType()))
                        .findFirst()
                        .ifPresent(event -> linkAndDescriptionPerChatIds
                                .computeIfAbsent(linkAndDescription, k -> new ArrayList<>())
                                .add(link.getChatId()));
            }

            markLinkVerified(link, lastEventId);
        }
        final long[] index = {0};
        linkAndDescriptionPerChatIds
            .forEach((linkAndDescription, chatIds) -> {
                var linkAndDescriptionArray = linkAndDescription.split(":::");
                notificationService.send(
                    new LinkUpdate(index[0]++, linkAndDescriptionArray[0], linkAndDescriptionArray[1], chatIds)
                );
        });
    }


    private void markLinkVerified(LinkModel link, String lastUpdatedId){
        link.setLastUpdatedId(lastUpdatedId);
        final int updateTimeout = 5;
        link.setLastChecked(new Date(new java.util.Date(
                System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(updateTimeout)
        ).getTime()));
        linkService.update(link);
    }
}
