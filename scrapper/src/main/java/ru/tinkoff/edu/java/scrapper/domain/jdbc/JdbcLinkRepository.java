package ru.tinkoff.edu.java.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @SuppressWarnings("checkstyle:magicnumber")
    public LinkModel add(LinkModel link) {
        String sql = "INSERT INTO link (link, chat_id, link_type, last_updated_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, link.getLink());
            ps.setLong(2, link.getChatId());
            ps.setString(3, link.getLinkType().toString());
            ps.setString(4, link.getLastUpdatedId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKeys() == null || keyHolder.getKeys().get("id") == null)
            return null;
        link.setId(Long.parseLong(keyHolder.getKeys().get("id").toString()));
        return link;
    }

    @SuppressWarnings("checkstyle:magicnumber")
    public LinkModel update(LinkModel link) {
        String sql =
                "UPDATE link SET link = ?, chat_id = ?, last_updated_id = ?, " +
                        "last_checked = ?, link_type = ?" +
                        "WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, link.getLink());
            ps.setLong(2, link.getChatId());
            ps.setString(3, link.getLastUpdatedId());
            ps.setDate(4, link.getLastChecked());
            ps.setString(5, link.getLinkType().toString());
            ps.setLong(6, link.getId());
            return ps;
        }, keyHolder);
        if (keyHolder.getKeys() == null || keyHolder.getKeys().get("id") == null)
            return null;
        return link;
    }

    public LinkModel removeByChatIdAndLink(long chatId, String link) {
        String sql = "DELETE FROM link WHERE chat_id = ? AND link = ?";
        LinkModel removedLink = findByChatIdAndLink(chatId, link);
        jdbcTemplate.update(sql, chatId, link);
        return removedLink;
    }

    public LinkModel findByChatIdAndLink(long chatId, String url) {
        String sql = "SELECT * FROM link WHERE chat_id = ? AND link = ?";
        RowMapper<LinkModel> rowMapper = (resultSet, rowNum) -> {
            LinkModel link = new LinkModel();
            link.setId(resultSet.getLong("id"));
            link.setLink(resultSet.getString("link"));
            link.setChatId(resultSet.getLong("chat_id"));
            link.setLastUpdatedId(resultSet.getString("last_updated_id"));
            link.setLastChecked(resultSet.getDate("last_checked"));
            link.setLinkType(LinkType.valueOf(resultSet.getString("link_type")));
            return link;
        };
        List<LinkModel> links = jdbcTemplate.query(sql, rowMapper, chatId, url);
        return links.isEmpty() ? null : links.get(0);
    }

    public List<LinkModel> findAllByTgChatId(long tgChatId) {
        return jdbcTemplate.query("SELECT * FROM link WHERE chat_id = ?", (resultSet, rowNum) ->
                new LinkModel(resultSet.getLong("id"),
                        resultSet.getString("link"),
                        resultSet.getLong("chat_id"),
                        resultSet.getString("last_updated_id"),
                        resultSet.getDate("last_checked"),
                        LinkType.valueOf(resultSet.getString("link_type"))
                ), tgChatId);
    }

    public List<LinkModel> findAllOldByLinkType(LinkType linkType) {
        return jdbcTemplate.query(
                "SELECT * FROM link WHERE link_type = ? AND CURRENT_TIMESTAMP > last_checked",
                (resultSet, rowNum) ->
                new LinkModel(resultSet.getLong("id"),
                        resultSet.getString("link"),
                        resultSet.getLong("chat_id"),
                        resultSet.getString("last_updated_id"),
                        resultSet.getDate("last_checked"),
                        LinkType.valueOf(resultSet.getString("link_type"))
                ), linkType.toString());
    }
}
