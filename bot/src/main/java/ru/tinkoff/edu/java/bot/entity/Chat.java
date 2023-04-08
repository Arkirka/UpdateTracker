package ru.tinkoff.edu.java.bot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// TODO: класс-заглушка для тестов. Позже можно переделать в entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {
    public Chat(Long id) {
        this.id = id;
    }

    private Long id;

    private List<Link> linkList;
}
