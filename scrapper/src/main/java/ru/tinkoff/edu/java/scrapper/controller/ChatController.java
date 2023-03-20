package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat/{id}")
public class ChatController {
    @PostMapping
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        // TODO: здесь нужно зарегистрировать чат
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        // TODO: здесь нужно удалить чат
        return ResponseEntity.ok().build();
    }
}
