package ru.tinkoff.edu.java.scrapper.service.notification.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.dto.bot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.notification.NotificationService;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements NotificationService {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    @Override
    public void send(LinkUpdate update) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, update);
    }
}
