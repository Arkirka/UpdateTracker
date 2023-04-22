package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import ru.tinkoff.edu.java.scrapper.model.LinkModel;
import ru.tinkoff.edu.java.scrapper.service.ChatService;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.util.LinkUtils;

import java.net.URI;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final LinkUtils linkUtils;
    private final ChatService chatService;
    private final DSLContext dslContext;
    private final Link linkTable = Link.LINK;

    @Override
    public Optional<LinkModel> add(long tgChatId, URI url) {
        if (!chatService.exists(tgChatId))
            return Optional.empty();

        LinkModel linkModel = new LinkModel();
        linkModel.setLink(url.getPath());
        linkModel.setChatId(tgChatId);
        linkModel.setLinkType(linkUtils.getLinkType(url));
        linkModel.setLastUpdatedId(linkUtils.getLastUpdatedId(linkModel.getLinkType(), url));

        LinkRecord linkRecord = dslContext.newRecord(linkTable, linkModel);
        linkRecord.store();

        return Optional.of(mapToModel(linkRecord));

    }

    @Override
    public Optional<LinkModel> update(LinkModel link) {
        LinkRecord linkRecord = dslContext.newRecord(linkTable, link);
        linkRecord.update();
        return Optional.of(mapToModel(linkRecord));
    }

    @Override
    public Optional<LinkModel> remove(long tgChatId, URI url) {
        var removedLink = findByTgChatIdAndLink(tgChatId, url);
        if (removedLink == null)
            return Optional.empty();

        boolean isRemoved = dslContext.deleteFrom(linkTable)
                .where(
                        linkTable.CHAT_ID.eq(tgChatId)
                                .and(linkTable.LINK_.eq(url.getPath()))
                )
                .execute() > 0;

        return isRemoved ? Optional.of(removedLink) : Optional.empty();
    }

    @Override
    public List<LinkModel> findAllByTgChatId(long tgChatId) {
        return dslContext.selectFrom(linkTable)
                .where(linkTable.CHAT_ID.eq(tgChatId))
                .fetch()
                .stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkModel> findAllOldByLinkType(LinkType linkType) {
        return dslContext.selectFrom(linkTable)
                .where(
                        linkTable.LINK_TYPE.eq(linkType.toString())
                                .and(linkTable.LAST_CHECKED.lessThan(LocalDateTime.now()))
                )
                .fetch()
                .stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    private LinkModel findByTgChatIdAndLink(long tgChatId, URI url) {
        var linkRecord = dslContext.selectFrom(linkTable)
                .where(
                        linkTable.CHAT_ID.eq(tgChatId)
                                .and(linkTable.LINK_.eq(url.getPath()))
                )
                .fetchOne();
        return linkRecord != null ? mapToModel(linkRecord) : null;
    }

    private LinkModel mapToModel(LinkRecord linkRecord) {
        return new LinkModel(
                linkRecord.getId(),
                linkRecord.getLink(),
                linkRecord.getChatId(),
                linkRecord.getLastUpdatedId(),
                Date.valueOf(Objects.requireNonNull(linkRecord.getLastChecked()).toLocalDate()),
                LinkType.valueOf(linkRecord.getLinkType())
        );
    }
}
