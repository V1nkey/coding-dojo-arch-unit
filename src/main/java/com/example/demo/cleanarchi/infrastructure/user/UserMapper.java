package com.example.demo.cleanarchi.infrastructure.user;

import com.example.demo.cleanarchi.domain.model.User;

public class UserMapper {
    private UserMapper() {}

    public static UserDatabase map(User user) {
        return new UserDatabase(user.getId(), user.getName(), user.getEmailAddress());
    }
}
