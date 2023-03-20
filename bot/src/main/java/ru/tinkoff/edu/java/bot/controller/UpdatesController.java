package ru.tinkoff.edu.java.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateResponse;

@RestController
@RequestMapping("/updates")
public class UpdatesController {

    @PostMapping
    public ResponseEntity<LinkUpdateResponse> createUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest){
        // TODO: здесь обработать обновление
        return ResponseEntity.ok(new LinkUpdateResponse());
    }
}
