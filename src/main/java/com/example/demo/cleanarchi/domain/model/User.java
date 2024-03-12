package com.example.demo.cleanarchi.domain.model;

public class User {

    private String id;
    private String name;
    private String emailAddress;

    public User(String name, String emailAddress) {
        this(null, name, emailAddress);
    }

    public User(String id, String name, String emailAddress) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
