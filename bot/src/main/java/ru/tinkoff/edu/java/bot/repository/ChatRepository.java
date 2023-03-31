package ru.tinkoff.edu.java.bot.repository;

import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.entity.Chat;

import java.util.List;

@Component
public class ChatRepository {
    private final List<Chat> chatRepository;

    public ChatRepository(List<Chat> chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> getAll(){
        return chatRepository;
    }

    public Chat save(Chat chat){
        chatRepository.add(chat);
        return chat;
    }

    public List<Chat> saveAll(List<Chat> chats){
        chatRepository.addAll(chats);
        return chats;
    }

}
