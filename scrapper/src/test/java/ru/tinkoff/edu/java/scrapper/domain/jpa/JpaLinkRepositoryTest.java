package ru.tinkoff.edu.java.scrapper.domain.jpa;

import migration.IntegrationEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;

import java.util.List;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationEnvironment{
    @Autowired
    private JpaLinkRepository jpaLinkRepository;
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    public void testAdd() {
        Chat chat = new Chat();
        chat = jpaChatRepository.save(chat);

        Link link = new Link(null, "https://example.com", "1",
                 null, LinkType.GITHUB.toString(), chat);
        jpaLinkRepository.save(link);

        var links = jpaLinkRepository.findAll();
        assertEquals(1, links.size());
        assertEquals(link.getId(), links.get(0).getId());

        jpaLinkRepository.deleteAll();
        jpaChatRepository.deleteAll();
    }

    @Test
    public void testRemove() {
        Chat chat = new Chat();
        chat = jpaChatRepository.save(chat);

        Link link = new Link(null, "https://example.com", "1",
                null, LinkType.GITHUB.toString(), chat);
        link = jpaLinkRepository.save(link);

        jpaLinkRepository.delete(link);

        assertTrue(jpaChatRepository.findById(link.getId()).isEmpty());

        jpaLinkRepository.deleteAll();
        jpaChatRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        Chat chat = jpaChatRepository.save(new Chat());

        List<Link> expected = LongStream.range(1L, 10L)
                .mapToObj(x -> new Link(null, "https://example.com", "1",
                        null, LinkType.GITHUB.toString(), chat))
                .toList();


        jpaLinkRepository.saveAll(expected);

        List<Link> actual = jpaLinkRepository.findAllByChat(chat);
        assertEquals(expected.size(), actual.size());

        jpaLinkRepository.deleteAll();
        jpaChatRepository.deleteAll();
    }

}
