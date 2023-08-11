package com.example.journalparser.code;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyControoler {

    private final ExtractAuthorFromFile resolver;

    @GetMapping("/")
    public ResponseEntity<?> fddf(){
        resolver.extract(1949);
        return ResponseEntity.ok().build();
    }
}
