package ru.tinkoff.edu.java.scrapper.configuration.dal;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public ChatService chatService(
            JdbcChatRepository tgChatRepository
    ) {
        return new JdbcChatService(tgChatRepository);
    }

    @Bean
    public LinkService linkService(
            JdbcLinkRepository linkRepository,
            ChatService chatService,
            LinkUtils linkUtils
    ) {
        return new JdbcLinkService(linkRepository, chatService, linkUtils);
    }
}
