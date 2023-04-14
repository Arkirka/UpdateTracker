package ru.tinkoff.edu.java.scrapper.dao;

import migration.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.*;

@SpringBootTest
public class LinkDaoTest extends IntegrationEnvironment {

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
        Link link = new Link(1L, "https://example.com", "TRACKED", 1L);
        Chat chat = new Chat(1L);

        chatDao.add(chat);
        linkDao.add(link);

        List<Link> links = linkDao.findAll();
        assertEquals(1, links.size());
        assertEquals(link.getId(), links.get(0).getId());

        linkDao.remove(link.getId());
        chatDao.remove(chat.getId());
    }

    @Test
    public void testRemove() {
        Link link = new Link(1L, "https://example.com", "UNTRACKED", 1L);
        Chat chat = new Chat(1L);

        chatDao.add(chat);
        linkDao.add(link);

        linkDao.remove(link.getId());

        List<Link> links = linkDao.findAll();
        assertTrue(links.isEmpty());

        chatDao.remove(chat.getId());
    }

    @Test
    public void testFindAll() {
        List<Link> expected = LongStream.range(0L, 10L)
                .mapToObj(x -> new Link(x, "https://example.com", "UNTRACKED", 1L))
                .toList();
        Chat chat = new Chat(1L);

        chatDao.add(chat);
        expected.forEach(linkDao::add);

        List<Link> actual = linkDao.findAll();
        assertFalse(actual.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).toString(), actual.get(i).toString());
        }

        expected.forEach(x -> linkDao.remove(x.getId()));
        chatDao.remove(chat.getId());
    }
}