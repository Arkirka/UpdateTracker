package ru.tinkoff.edu.java.scrapper.configuration.notificatiton;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.service.notification.NotificationService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpNotificationConfiguration {

    @Bean
    public NotificationService notificationService(BotClient botClient){
        return botClient;
    }
}
