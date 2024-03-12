package com.example.demo.screamingarchitecture.book.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @PostMapping
    public ResponseEntity<Void> addBook(BookRequest bookRequest) {
        return ResponseEntity.ok().build();
    }
}
