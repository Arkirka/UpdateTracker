package ru.tinkoff.edu.java.bot.service.notification;

import ru.tinkoff.edu.java.bot.dto.LinkUpdate;

public interface NotificationService {
    void processUpdate(LinkUpdate update);
}
