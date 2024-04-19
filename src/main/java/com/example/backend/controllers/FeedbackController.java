package com.example.backend.controllers;

import com.example.backend.dtos.FeedbackDto;
import com.example.backend.services.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class FeedbackController {

    private final FeedbackService feedbackService;
    @PostMapping("/addFeedback")
    public ResponseEntity<String> addFeedback(@RequestBody @Valid FeedbackDto feedbackDto) {

        feedbackService.addFeedback(feedbackDto);

        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/getFeedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbacks() {
        return ResponseEntity.ok(feedbackService.getFeedbacks());
    }
}
