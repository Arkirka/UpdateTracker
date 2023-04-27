package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import migration.IntegrationEnvironment;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.model.ChatModel;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {

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
        LinkModel link = new LinkModel(1L, "https://example.com", 1L,
                null, null, LinkType.GITHUB);
        ChatModel chat = new ChatModel(1L);

        jdbcChatRepository.add(chat);
        jdbcLinkRepository.add(link);

        List<LinkModel> links = jdbcLinkRepository.findAllByTgChatId(chat.getId());
        assertEquals(1, links.size());
        assertEquals(link.getId(), links.get(0).getId());

        jdbcLinkRepository.removeByChatIdAndLink(link.getChatId(), link.getLink());
        jdbcChatRepository.remove(chat.getId());
    }

    @Test
    public void testRemove() {
        LinkModel link = new LinkModel(1L, "https://example.com", 1L,
                null, null, LinkType.GITHUB);
        ChatModel chat = new ChatModel(1L);

        jdbcChatRepository.add(chat);
        jdbcLinkRepository.add(link);

        jdbcLinkRepository.removeByChatIdAndLink(link.getChatId(), link.getLink());

        List<LinkModel> links = jdbcLinkRepository.findAllByTgChatId(chat.getId());
        assertTrue(links.isEmpty());

        jdbcChatRepository.remove(chat.getId());
    }

    @Test
    public void testFindAll() {
        List<LinkModel> expected = LongStream.range(0L, 10L)
                .mapToObj(x -> new LinkModel(x,
                        "https://example.com",
                        1L,
                        null,
                        null,
                        LinkType.GITHUB))
                .toList();
        ChatModel chat = new ChatModel(1L);

        jdbcChatRepository.add(chat);
        expected.forEach(jdbcLinkRepository::add);

        List<LinkModel> actual = jdbcLinkRepository.findAllByTgChatId(chat.getId());
        assertFalse(actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actual.get(i).toString());
        }

        expected.forEach(x -> jdbcLinkRepository.removeByChatIdAndLink(x.getChatId(), x.getLink()));
        jdbcChatRepository.remove(chat.getId());
    }
}