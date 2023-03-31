package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HelpCommand implements Command{
    private final List<? extends Command> commands;

    public HelpCommand(List<? extends Command> commands) {
        this.commands = commands;
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        log.info("Help command received from: " + update.message().from().id());
        String commandsListText = commands.stream()
                .map(x -> x.command() + " : " + x.description())
                .collect(Collectors.joining("\n"));
        return new SendMessage(update.message().chat().id(), commandsListText);
    }
}
