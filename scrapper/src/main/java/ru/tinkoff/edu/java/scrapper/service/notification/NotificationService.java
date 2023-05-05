package ru.tinkoff.edu.java.scrapper.service.notification;

import ru.tinkoff.edu.java.scrapper.dto.bot.LinkUpdate;

public interface NotificationService {
    void send(LinkUpdate update);
}
