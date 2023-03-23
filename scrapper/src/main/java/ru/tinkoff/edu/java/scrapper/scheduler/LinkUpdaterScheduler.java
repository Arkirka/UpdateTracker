package ru.tinkoff.edu.java.scrapper.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class LinkUpdaterScheduler {
    private final Logger logger = LoggerFactory.getLogger(LinkUpdaterScheduler.class);

    @Scheduled(fixedDelayString = "#{@scheduler.interval.toMillis()}000")
    public void update() {
        logger.info("Updating links...");
        // implement link updating logic here
    }
}
