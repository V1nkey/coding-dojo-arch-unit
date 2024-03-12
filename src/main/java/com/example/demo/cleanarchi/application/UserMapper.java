package com.example.demo.cleanarchi.application;

import com.example.demo.cleanarchi.domain.model.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User map(UserRequest userRequest) {
        return new User(userRequest.name(), userRequest.emailAddress());
    }
}
