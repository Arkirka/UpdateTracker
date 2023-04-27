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
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;

import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(initializers = {JpaChatRepositoryTest.Initializer.class})
public class JpaChatRepositoryTest extends IntegrationEnvironment {
    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = IntegrationEnvironment.getInstance();
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

        chat = jpaChatRepository.save(chat);

        List<Chat> chatList = jpaChatRepository.findAll();
        assertEquals(1, chatList.size());
        assertEquals(chat.getId(), chatList.get(0).getId());

        jpaChatRepository.deleteAll();
    }

    @Test
    public void testRemove() {
        Chat chat = new Chat(1L);

        chat = jpaChatRepository.save(chat);

        jpaChatRepository.delete(chat);

        List<Chat> chatList = jpaChatRepository.findAll();
        assertTrue(chatList.isEmpty());
    }

    @Test
    public void testFindAll() {
        List<Chat> expected = LongStream.range(0L, 10L)
                .mapToObj(Chat::new)
                .toList();

        expected = jpaChatRepository.saveAll(expected);

        List<Chat> actual = jpaChatRepository.findAll();
        assertFalse(actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actual.get(i).toString());
        }

        jpaChatRepository.deleteAll();
    }
}