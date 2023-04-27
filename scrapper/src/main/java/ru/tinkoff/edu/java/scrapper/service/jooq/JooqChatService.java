package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RequiredArgsConstructor
public class JooqChatService implements ChatService {

    private final DSLContext dslContext;
    private final Chat chat = Chat.CHAT;

    @Override
    public void register(long tgChatId) {
        dslContext.insertInto(chat, chat.ID).values(tgChatId).execute();
    }

    @Override
    public void unregister(long tgChatId) {
        dslContext.deleteFrom(chat).where(chat.ID.eq(tgChatId)).execute();
    }

    @Override
    public boolean exists(long tgChatId) {
        var result = dslContext.select()
                .from(chat)
                .where(chat.ID.eq(tgChatId))
                .fetch();
        return result.isNotEmpty();
    }
}
