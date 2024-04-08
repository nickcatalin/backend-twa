package com.example.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @GetMapping("/login")
    public ResponseEntity<List<String>> login() {
        return ResponseEntity.ok(List.of("Hello", "World"));
    }

}
