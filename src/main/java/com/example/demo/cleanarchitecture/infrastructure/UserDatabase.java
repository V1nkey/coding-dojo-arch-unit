package com.example.demo.cleanarchitecture.infrastructure;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class UserDatabase {
    @Id private String id;
    private LocalDate creationDate;
    private String name;
    private String emailAddress;

    public UserDatabase() {
        this.creationDate = LocalDate.now();
    }
}
