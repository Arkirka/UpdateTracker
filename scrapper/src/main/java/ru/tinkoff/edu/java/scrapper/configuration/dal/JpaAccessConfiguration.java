package ru.tinkoff.edu.java.scrapper.configuration.dal;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public ChatService chatService(
            JpaChatRepository jpaChatRepository
    ) {
        return new JpaChatService(jpaChatRepository);
    }

    @Bean
    public LinkService linkService(
            JpaChatRepository jpaChatRepository,
            JpaLinkRepository jpaLinkRepository,
            LinkUtils linkUtils
    ) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository, linkUtils);
    }
}
