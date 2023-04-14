package ru.tinkoff.edu.java.scrapper.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.Chat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatDao {
    private final JdbcTemplate jdbcTemplate;

    public void add(Chat chat) {
        jdbcTemplate.update("INSERT INTO chat (id) VALUES (?)", chat.getId());
    }

    public void remove(long chatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE id = ?", chatId);
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat", (resultSet, rowNum) ->
                new Chat(resultSet.getLong("id")));
    }
}
