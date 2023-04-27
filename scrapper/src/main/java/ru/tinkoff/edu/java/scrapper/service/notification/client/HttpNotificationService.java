package ru.tinkoff.edu.java.scrapper.service.notification.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.client.bot.BotClient;
import ru.tinkoff.edu.java.scrapper.dto.bot.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.service.notification.NotificationService;

@Service
@RequiredArgsConstructor
public class HttpNotificationService implements NotificationService {
    private final BotClient botClient;
    @Override
    public void send(LinkUpdate update) {
        botClient.sendNotification(update);
    }
}
