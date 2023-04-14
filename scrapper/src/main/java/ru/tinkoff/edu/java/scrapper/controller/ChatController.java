package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.service.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tg-chat/{id}")
public class ChatController {
    private final ChatService chatService;
    @PostMapping
    public ResponseEntity<?> registerChat(@PathVariable Long id) {
        if (chatService.exists(id))
            return new ResponseEntity<>("Chat already registered!", HttpStatus.BAD_REQUEST);
        chatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
