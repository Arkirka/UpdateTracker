package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

@RestController
@RequestMapping("/links")
public class LinkController {

    @GetMapping
    public ResponseEntity<ListLinksResponse> getAll(@RequestHeader("Tg-Chat-Id") Long tgChatId){
        // TODO: здесь нужно получить ссылки
        return ResponseEntity.ok(new ListLinksResponse());
    }

    @PostMapping
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        // TODO: здесь нужно добавить отслеживание ссылки
        return ResponseEntity.ok(new LinkResponse());
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        // TODO: здесь нужно убрать отслеживание ссылки
        return ResponseEntity.ok(new LinkResponse());
    }
}
