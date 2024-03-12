package com.example.demo.cleanarchitecture.application;

import com.example.demo.cleanarchitecture.domain.model.User;

public class UserMapper {

    public static User map(UserRequest userRequest) {
        return new User();
    }
}
