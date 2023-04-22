package ru.tinkoff.edu.java.scrapper.domain.jpa;

import migration.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;

import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(initializers = {JpaLinkRepositoryTest.Initializer.class})
public class JpaLinkRepositoryTest extends IntegrationEnvironment{
    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = IntegrationEnvironment.getInstance();
    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    @Autowired
    private JpaChatRepository jpaChatRepository;

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword(),
                    "spring.datasource.driver-class-name=" + postgresqlContainer.getDriverClassName()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void testAdd() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://example.com", "1",
                 null, LinkType.GITHUB.toString(), chat);

        jpaChatRepository.save(chat);
        jpaLinkRepository.save(link);

        var links = jpaLinkRepository.findAll();
        assertEquals(1, links.size());
        assertEquals(link.getId(), links.get(0).getId());

        jpaLinkRepository.deleteAll();
        jpaChatRepository.deleteAll();
    }

    @Test
    public void testRemove() {
        Chat chat = new Chat(1L);
        Link link = new Link(1L, "https://example.com", "1",
                null, LinkType.GITHUB.toString(), chat);

        jpaChatRepository.save(chat);
        jpaLinkRepository.save(link);

        jpaLinkRepository.delete(link);

        var links = jpaLinkRepository.findAll();
        assertTrue(links.isEmpty());

        jpaLinkRepository.deleteAll();
        jpaChatRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        Chat chat = new Chat(1L);
        List<Link> expected = LongStream.range(0L, 10L)
                .mapToObj(x -> new Link(x, "https://example.com", "1",
                        null, LinkType.GITHUB.toString(), chat))
                .toList();


        jpaChatRepository.save(chat);
        jpaLinkRepository.saveAll(expected);

        List<Link> actual = jpaLinkRepository.findAllByChat(chat);
        assertFalse(actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actual.get(i).toString());
        }

        jpaLinkRepository.deleteAll();
        jpaChatRepository.deleteAll();
    }

}
