package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByChatAndLink(Chat chat, String link);

    List<Link> findAllByChat(Chat chat);

    @Query("SELECT l FROM Link l WHERE l.lastChecked < :currentTime AND l.linkType = :linkType")
    List<Link> findAllByLinkTypeAndLastCheckedBefore(@Param("currentTime") Timestamp currentTime,
                                          @Param("linkType") String linkType);
}
