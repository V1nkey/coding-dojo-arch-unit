package com.example.demo.screamingarchitecture.book;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Book {

    @Id
    private String id;
    private String name;
    private String author;
    private LocalDate releaseDate;

}
