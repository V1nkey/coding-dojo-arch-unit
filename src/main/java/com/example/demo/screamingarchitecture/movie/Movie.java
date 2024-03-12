package com.example.demo.screamingarchitecture.movie;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Movie {

    @Id
    private String id;
    private String name;
    private String director;
    private LocalDate releaseDate;
}
