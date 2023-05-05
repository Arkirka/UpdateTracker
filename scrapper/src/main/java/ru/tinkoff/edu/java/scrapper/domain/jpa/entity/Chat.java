package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "chat")
public class Chat {
    public Chat(Long id){
        this.id = id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "chat")
    private List<Link> linkList;
}
