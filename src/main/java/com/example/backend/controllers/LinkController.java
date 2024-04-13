package com.example.backend.controllers;

import com.example.backend.dtos.LinkDto;
import com.example.backend.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/getLinks")
    public ResponseEntity<List<LinkDto>> getLinks() {

        List<LinkDto> links = linkService.getLinks();

        return ResponseEntity.ok(links);
    }
}
