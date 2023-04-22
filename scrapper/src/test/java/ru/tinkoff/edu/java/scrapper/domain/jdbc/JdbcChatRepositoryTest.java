package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import migration.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.tinkoff.edu.java.scrapper.model.ChatModel;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationEnvironment {
    @Container
    public PostgreSQLContainer<?> postgresqlContainer = IntegrationEnvironment.getInstance();
    private final JdbcLinkRepository jdbcLinkRepository = new JdbcLinkRepository(new JdbcTemplate(testDataSource()));
    private final JdbcChatRepository jdbcChatRepository = new JdbcChatRepository(new JdbcTemplate(testDataSource()));

    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresqlContainer.getDriverClassName());
        dataSource.setUrl(postgresqlContainer.getJdbcUrl());
        dataSource.setUsername(postgresqlContainer.getUsername());
        dataSource.setPassword(postgresqlContainer.getPassword());

        return dataSource;
    }

    @Test
    public void testAdd() {
        ChatModel chat = new ChatModel(1L);

        jdbcChatRepository.add(chat);

        List<ChatModel> chatList = jdbcChatRepository.findAll();
        assertEquals(1, chatList.size());
        assertEquals(chat.getId(), chatList.get(0).getId());

        jdbcChatRepository.remove(chat.getId());
    }

    @Test
    public void testRemove() {
        ChatModel chat = new ChatModel(1L);

        jdbcChatRepository.add(chat);

        jdbcChatRepository.remove(chat.getId());

        List<LinkModel> links = jdbcLinkRepository.findAllByTgChatId(chat.getId());
        assertTrue(links.isEmpty());

        jdbcChatRepository.remove(chat.getId());
    }

    @Test
    public void testFindAll() {
        List<ChatModel> expected = LongStream.range(0L, 10L)
                .mapToObj(ChatModel::new)
                .toList();

        expected.forEach(jdbcChatRepository::add);

        List<ChatModel> actual = jdbcChatRepository.findAll();
        assertFalse(actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actual.get(i).toString());
        }

        expected.forEach(x -> jdbcChatRepository.remove(x.getId()));
    }
}
