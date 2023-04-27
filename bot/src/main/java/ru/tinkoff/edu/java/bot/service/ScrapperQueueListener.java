package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.notification.NotificationService;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "${app.scrapper-queue.name}")
public class ScrapperQueueListener {
    private final NotificationService notificationService;

    @RabbitHandler
    public void receiver(LinkUpdate update) {
        notificationService.processUpdate(update);
    }
}
