package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.client.github.GitHubClient;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/links")
public class LinkController {
    private final LinkService linkService;
    // TODO: remove this field
    private final GitHubClient gitHubClient;

    @GetMapping
    public ResponseEntity<ListLinksResponse> getAll(@RequestHeader("Tg-Chat-Id") Long tgChatId){
        var list = linkService.findAllByTgChatId(tgChatId).stream()
                .map(x -> new LinkResponse(x.getId(), x.getLink()))
                .toList();
        return ResponseEntity.ok(
                new ListLinksResponse(list, list.size())
        );
    }

    @PostMapping
    public ResponseEntity<?> addLink(@RequestHeader("Tg-Chat-Id") Long tgChatId,
                                     @RequestBody AddLinkRequest request) {
        var link = linkService.add(tgChatId, URI.create(request.getLink()));
        if (link.isEmpty())
            return new ResponseEntity<>("Unable to add link", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok(
                new LinkResponse(link.get().getId(), link.get().getLink())
        );
    }

    @DeleteMapping
    public ResponseEntity<?> removeLink(@RequestHeader("Tg-Chat-Id") Long tgChatId,
                                                   @RequestBody RemoveLinkRequest request) {
        var link = linkService.remove(tgChatId, URI.create(request.getLink()));
        if (link.isEmpty())
            return new ResponseEntity<>("Unable to remove link", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok(
                new LinkResponse(link.get().getId(), link.get().getLink())
        );
    }

    // TODO: remove this method
    @GetMapping
    @RequestMapping("/{owner}/{repo}")
    public ResponseEntity<?> testClient(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        var some = gitHubClient.fetchLastRepositoryEvent(owner, repo);
        return ResponseEntity.ok(some);
    }
}
