package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrackCommand implements Command{
    @Override
    public String command() {
        return "/track ";
    }

    @Override
    public String description() {
        return "Начать отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("Track command received from: " + update.message().from().id());
        String userName = update.message().from().firstName() + " " + update.message().from().lastName();
        return new SendMessage(update.message().chat().id(), "Добро пожаловать, " + userName + "!");
    }
}
