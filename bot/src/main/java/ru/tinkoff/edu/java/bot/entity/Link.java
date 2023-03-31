package ru.tinkoff.edu.java.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: класс-заглушка для тестов. Позже можно переделать в entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private Long id;
    private String link;
    private LinkStatus linkStatus;
    private Chat chat;
}
