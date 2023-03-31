package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.bot.entity.Link;
import ru.tinkoff.edu.java.bot.entity.LinkStatus;
import ru.tinkoff.edu.java.bot.repository.LinkRepository;

import java.util.List;

@Slf4j
public class ListCommand implements Command{
    private final LinkRepository linkRepository;

    public ListCommand(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

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
        log.info("List command received from: " + update.message().chat().id());

        Long chatId = update.message().chat().id();
        List<Link> links = linkRepository.getAllByChatId(chatId)
                .stream()
                .filter(link -> link.getLinkStatus().equals(LinkStatus.TRACKED))
                .toList();
        if (links.isEmpty()) {
            return new SendMessage(chatId, "Список отслеживаемых ссылок пуст");
        } else {
            StringBuilder sb = new StringBuilder("Список отслеживаемых ссылок:\n");
            for (Link link : links) {
                sb.append(link.getLink()).append("\n");
            }
            return new SendMessage(chatId, sb.toString());
        }
    }
}