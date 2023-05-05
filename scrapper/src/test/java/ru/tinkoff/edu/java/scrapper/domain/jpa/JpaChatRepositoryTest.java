package ru.tinkoff.edu.java.scrapper.domain.jpa;

import migration.IntegrationEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    public void testAdd() {
        Chat chat = new Chat();

        chat.setId(1L);
        chat = jpaChatRepository.save(chat);

        List<Chat> chatList = jpaChatRepository.findAll();
        assertEquals(1, chatList.size());
        assertEquals(chat.getId(), chatList.get(0).getId());

        jpaChatRepository.deleteAll();
    }

    @Test
    @Transactional
    @Rollback
    public void testRemove() {
        Chat chat = new Chat(1L);

        chat = jpaChatRepository.save(chat);

        jpaChatRepository.delete(chat);

        List<Chat> chatList = jpaChatRepository.findAll();
        assertTrue(chatList.isEmpty());

        jpaChatRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testFindAll() {
        Chat chat1 = new Chat();
        jpaChatRepository.save(chat1);

        Chat chat2 = new Chat();
        jpaChatRepository.save(chat2);

        Chat chat3 = new Chat();
        jpaChatRepository.save(chat3);

        List<Chat> chatList = jpaChatRepository.findAll();
        assertEquals(3, chatList.size());

        jpaChatRepository.deleteAll();
    }
}