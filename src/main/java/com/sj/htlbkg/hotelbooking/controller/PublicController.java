package com.sj.htlbkg.hotelbooking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class PublicController {

    @GetMapping
    public ResponseEntity<String> open() {
        return ResponseEntity.ok("Hello world!");
    }
}
