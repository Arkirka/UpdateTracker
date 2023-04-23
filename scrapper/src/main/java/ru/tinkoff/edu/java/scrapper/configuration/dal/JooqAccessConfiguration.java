package ru.tinkoff.edu.java.scrapper.configuration.dal;

import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {

    @Bean
    public ChatService chatService(
            DSLContext dslContext
    ) {
        return new JooqChatService(dslContext);
    }

    @Bean
    public LinkService linkService(
            DSLContext dslContext,
            ChatService chatService,
            LinkUtils linkUtils
    ) {
        return new JooqLinkService(linkUtils, chatService, dslContext);
    }
}
