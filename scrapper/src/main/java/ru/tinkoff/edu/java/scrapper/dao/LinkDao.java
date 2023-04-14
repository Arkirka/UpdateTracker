package ru.tinkoff.edu.java.scrapper.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.Link;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LinkDao {
    private final JdbcTemplate jdbcTemplate;

    public void add(Link link) {
        String sql = "INSERT INTO link (id, link, link_status, chat_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, link.getId(), link.getLink(), link.getLinkStatus(), link.getChatId());
    }

    public void remove(long linkId) {
        jdbcTemplate.update("DELETE FROM link WHERE id = ?", linkId);
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM link", (resultSet, rowNum) ->
                new Link(resultSet.getLong("id"),
                        resultSet.getString("link"),
                        resultSet.getString("link_status"),
                        resultSet.getLong("chat_id")));
    }
}
