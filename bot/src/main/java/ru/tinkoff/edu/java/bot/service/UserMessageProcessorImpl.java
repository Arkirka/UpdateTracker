package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.command.*;

import java.util.ArrayList;
import java.util.List;

@Service("userMessageProcessor")
public class UserMessageProcessorImpl implements UserMessageProcessor{
    private final List<Command> commands;

    public UserMessageProcessorImpl() {
        commands = new ArrayList<>();
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand());
        commands.add(new HelpCommand(commands()));
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commands)
            if (command.supports(update))
                return command.handle(update);
        return new SendMessage(update.message().chat().id(), "Unknown command");
    }
}
