package com.example.demo.cleanarchitecture.infrastructure;

import com.example.demo.cleanarchitecture.domain.model.User;

public class UserMapper {

    public static UserDatabase map(User user) {
        return new UserDatabase();
    }
}
