package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListCommand implements Command{
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("List command received from: " + update.message().from().id());
        String userName = update.message().from().firstName() + " " + update.message().from().lastName();
        return new SendMessage(update.message().chat().id(), "Список ссылок");
    }
}