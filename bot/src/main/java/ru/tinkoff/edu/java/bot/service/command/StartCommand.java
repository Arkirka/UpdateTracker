package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartCommand implements Command{
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("Start command received from: " + update.message().from().id());
        String userName = update.message().from().firstName() + " " + update.message().from().lastName();
        return new SendMessage(update.message().chat().id(), "Добро пожаловать, " + userName + "!");
    }
}
