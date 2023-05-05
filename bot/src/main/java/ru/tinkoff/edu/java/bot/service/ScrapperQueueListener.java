package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.notification.NotificationService;

@RequiredArgsConstructor
@EnableRabbit
@Component
public class ScrapperQueueListener {
    private final NotificationService notificationService;

    @RabbitListener(queues = "${app.rabbitMQConfig.queue-name}")
    public void receiver(LinkUpdate update) {
        notificationService.processUpdate(update);
    }
}
