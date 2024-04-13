package com.example.backend.controllers;

import com.example.backend.dtos.LinkDto;
import com.example.backend.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/getLinks")
    public ResponseEntity<List<LinkDto>> getLinks() {

        List<LinkDto> links = linkService.getLinks();

        return ResponseEntity.ok(links);
    }
}
