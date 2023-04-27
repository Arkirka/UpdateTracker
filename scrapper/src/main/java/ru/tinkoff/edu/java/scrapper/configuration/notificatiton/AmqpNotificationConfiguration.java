package ru.tinkoff.edu.java.scrapper.configuration.notificatiton;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.service.notification.NotificationService;
import ru.tinkoff.edu.java.scrapper.service.notification.amqp.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class AmqpNotificationConfiguration {

    @Bean
    public NotificationService notificationService(RabbitTemplate rabbitTemplate,
                                                   String exchangeName,
                                                   String routingKey){
        return new ScrapperQueueProducer(rabbitTemplate, exchangeName, routingKey);
    }
}
