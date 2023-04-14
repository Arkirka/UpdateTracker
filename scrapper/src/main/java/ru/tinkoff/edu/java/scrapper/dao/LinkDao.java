package ru.tinkoff.edu.java.scrapper.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.model.Link;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LinkDao {
    private final JdbcTemplate jdbcTemplate;

    public Link add(Link link) {
        String sql = "INSERT INTO link (link, link_status, chat_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, link.getLink());
            ps.setString(2, link.getLinkStatus());
            ps.setLong(3, link.getChatId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKeys() == null || keyHolder.getKeys().get("id") == null)
            return null;
        link.setId(Long.parseLong(keyHolder.getKeys().get("id").toString()));
        return link;
    }

    public Link removeByChatIdAndLink(long chatId, String link) {
        String sql = "DELETE FROM link WHERE chat_id = ? AND link = ?";
        Link removedLink = findByChatIdAndLink(chatId, link); // Получаем объект Link перед удалением
        jdbcTemplate.update(sql, chatId, link);
        return removedLink;
    }

    public Link findByChatIdAndLink(long chatId, String url) {
        String sql = "SELECT * FROM link WHERE chat_id = ? AND link = ?";
        RowMapper<Link> rowMapper = (resultSet, rowNum) -> {
            Link link = new Link();
            link.setId(resultSet.getLong("id"));
            link.setLink(resultSet.getString("link"));
            link.setLinkStatus(resultSet.getString("link_status"));
            link.setChatId(resultSet.getLong("chat_id"));
            return link;
        };
        List<Link> links = jdbcTemplate.query(sql, rowMapper, chatId, url);
        return links.isEmpty() ? null : links.get(0);
    }


    public List<Link> findAll() {
        return jdbcTemplate.query("SELECT * FROM link", (resultSet, rowNum) ->
                new Link(resultSet.getLong("id"),
                        resultSet.getString("link"),
                        resultSet.getString("link_status"),
                        resultSet.getLong("chat_id")));
    }
}
