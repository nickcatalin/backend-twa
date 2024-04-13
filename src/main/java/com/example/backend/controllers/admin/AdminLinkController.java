package com.example.backend.controllers.admin;

import com.example.backend.dtos.LinkDto;
import com.example.backend.services.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminLinkController {

    private final LinkService linkService;

    @PostMapping("/addLink")
    public ResponseEntity<String> addLink(@RequestBody @Valid LinkDto linkDto) {
        linkService.saveLink(linkDto);
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/getLinks")
    public ResponseEntity<List<LinkDto>> getLinks() {

        List<LinkDto> links = linkService.getLinks();

        return ResponseEntity.ok(links);
    }

    @PutMapping("/updateLink/{id}")
    public ResponseEntity<String> updateLink(@PathVariable Long id, @RequestBody @Valid LinkDto linkDto) {
        linkService.updateLink(id, linkDto);
        return ResponseEntity.ok("Link updated successfully");
    }

    @DeleteMapping("/deleteLink/{id}")
    public ResponseEntity<String> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.ok("Link deleted successfully");
    }
}
