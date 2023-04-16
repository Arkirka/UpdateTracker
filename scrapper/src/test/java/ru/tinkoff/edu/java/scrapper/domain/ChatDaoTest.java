package ru.tinkoff.edu.java.scrapper.domain;

import migration.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.ChatDao;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.LinkDao;
import ru.tinkoff.edu.java.scrapper.model.ChatModel;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ChatDaoTest extends IntegrationEnvironment {
    @Container
    public PostgreSQLContainer<?> postgresqlContainer = IntegrationEnvironment.getInstance();
    private final LinkDao linkDao = new LinkDao(new JdbcTemplate(testDataSource()));
    private final ChatDao chatDao = new ChatDao(new JdbcTemplate(testDataSource()));

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

        chatDao.add(chat);

        List<ChatModel> chatList = chatDao.findAll();
        assertEquals(1, chatList.size());
        assertEquals(chat.getId(), chatList.get(0).getId());

        chatDao.remove(chat.getId());
    }

    @Test
    public void testRemove() {
        ChatModel chat = new ChatModel(1L);

        chatDao.add(chat);

        chatDao.remove(chat.getId());

        List<LinkModel> links = linkDao.findAllByTgChatId(chat.getId());
        assertTrue(links.isEmpty());

        chatDao.remove(chat.getId());
    }

    @Test
    public void testFindAll() {
        List<ChatModel> expected = LongStream.range(0L, 10L)
                .mapToObj(ChatModel::new)
                .toList();

        expected.forEach(chatDao::add);

        List<ChatModel> actual = chatDao.findAll();
        assertFalse(actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actual.get(i).toString());
        }

        expected.forEach(x -> chatDao.remove(x.getId()));
    }
}
