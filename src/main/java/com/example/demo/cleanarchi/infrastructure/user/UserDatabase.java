package com.example.demo.cleanarchi.infrastructure.user;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class UserDatabase {

    @Id
    private String id;
    private String name;
    private String emailAddress;
    private LocalDate registrationDate;

    public UserDatabase(String id, String name, String emailAddress) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.registrationDate = LocalDate.now();
    }
}
