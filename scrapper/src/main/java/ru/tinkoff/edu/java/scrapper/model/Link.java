package ru.tinkoff.edu.java.scrapper.model;

import lombok.*;
import ru.tinkoff.edu.java.scrapper.constant.LinkType;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Link {
    private long id;
    private String link;
    private long chatId;
    private String lastUpdatedId;
    private Date lastChecked;
    private LinkType linkType;
}
