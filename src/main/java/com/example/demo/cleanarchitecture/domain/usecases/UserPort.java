package com.example.demo.cleanarchitecture.domain.usecases;

import com.example.demo.cleanarchitecture.domain.model.User;

public interface UserPort {

    void registerUser(User user);
}
