package com.example.backend.controllers;

import com.example.backend.dtos.RatingDto;
import com.example.backend.services.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/updateRating/{linkId}")
    public ResponseEntity<String> updateRating(@PathVariable Long linkId, @RequestBody @Valid RatingDto ratingDto) {

        ratingService.saveRating(ratingDto, linkId);

        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/getRating/{linkId}")
    public ResponseEntity<RatingDto> getRating(@PathVariable Long linkId) {
        return ResponseEntity.ok(ratingService.getRating(linkId));
    }

    @GetMapping("/getAverageRating/{linkId}")
    public ResponseEntity<Long> getAverageRating(@PathVariable Long linkId) {
        return ResponseEntity.ok(ratingService.getAverageRating(linkId));
    }
}
