package ru.tinkoff.edu.java.scrapper.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Link {
    private long id;
    private String link;
    private long chatId;
}
