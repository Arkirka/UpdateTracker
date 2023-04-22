package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.ChatModel;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public ChatModel add(ChatModel chat) {
        String sql = "INSERT INTO chat (id) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, chat.getId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() == null)
            return null;
        chat.setId(keyHolder.getKey().longValue());
        return chat;
    }

    public ChatModel remove(long chatId) {
        String sql = "DELETE FROM chat WHERE id = ?";
        ChatModel removedChat = findById(chatId);
        jdbcTemplate.update(sql, chatId);
        return removedChat;
    }

    public ChatModel findById(long chatId) {
        String sql = "SELECT * FROM chat WHERE id = ?";
        RowMapper<ChatModel> rowMapper = (resultSet, rowNum) -> new ChatModel(resultSet.getLong("id"));
        List<ChatModel> chats = jdbcTemplate.query(sql, rowMapper, chatId);
        return chats.isEmpty() ? null : chats.get(0);
    }

    public List<ChatModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat", (resultSet, rowNum) ->
                new ChatModel(resultSet.getLong("id")));
    }

    public boolean exists(long chatId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM chat WHERE id = ?", Integer.class, chatId);
        return count != null && count > 0;
    }
}
