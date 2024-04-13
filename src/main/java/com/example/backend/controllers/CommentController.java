package com.example.backend.controllers;

import com.example.backend.dtos.CommentDto;
import com.example.backend.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/addComment/{linkId}")
    public ResponseEntity<String> addComment(@PathVariable Long linkId, @RequestBody @Valid CommentDto commentDto) {
        commentService.saveComment(commentDto, linkId);
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/getComments/{linkId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long linkId) {
        return ResponseEntity.ok(commentService.getComments(linkId));
    }

}
