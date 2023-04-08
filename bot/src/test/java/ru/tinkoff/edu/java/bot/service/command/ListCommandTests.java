package ru.tinkoff.edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.tinkoff.edu.java.bot.entity.Chat;
import ru.tinkoff.edu.java.bot.entity.Link;
import ru.tinkoff.edu.java.bot.entity.LinkStatus;
import ru.tinkoff.edu.java.bot.repository.LinkRepository;

import java.util.List;


class ListCommandTests {

    private ListCommand command;
    private LinkRepository linkRepository;

    @Test
    void handle_ShouldReturnTrackedLinkListMessage() {
        linkRepository = new LinkRepository(getLinks());
        command = new ListCommand(linkRepository);

        long chatId = 1;

        List<Link> links = getLinks()
                .stream()
                .filter(link -> link.getLinkStatus().equals(LinkStatus.TRACKED))
                .toList();
        StringBuilder sb = new StringBuilder("Список отслеживаемых ссылок:\n");
        for (Link link : links) {
            sb.append(link.getLink()).append("\n");
        }

        SendMessage expected = new SendMessage(chatId, sb.toString());
        SendMessage source = command.handle(getMockUpdate(chatId));

        Assertions.assertEquals(expected.getParameters().get("text"), source.getParameters().get("text"));
    }

    @Test
    void handle_ShouldReturnEmptyLinkListMessage() {
        linkRepository = new LinkRepository(getLinksEmpty());
        command = new ListCommand(linkRepository);
        long chatId = 1;

        SendMessage expected = new SendMessage(chatId, "Список отслеживаемых ссылок пуст");
        SendMessage source = command.handle(getMockUpdate(chatId));

        Assertions.assertEquals(expected.getParameters().get("text"), source.getParameters().get("text"));
    }

    public List<Link> getLinks(){
        return List.of(
                new Link(
                        1L, "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                        LinkStatus.TRACKED,
                        new Chat(1L)
                ),
                new Link(
                        1L, "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                        LinkStatus.UNTRACKED,
                        new Chat(1L)
                )
        );
    }

    public List<Link> getLinksEmpty(){
        return List.of();
    }

    private Update getMockUpdate(long chatId) {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        com.pengrad.telegrambot.model.Chat chat = Mockito.mock(com.pengrad.telegrambot.model.Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(chatId);
        return update;
    }
}