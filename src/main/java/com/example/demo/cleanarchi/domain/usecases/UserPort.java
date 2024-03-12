package com.example.demo.cleanarchi.domain.usecases;

import com.example.demo.cleanarchi.domain.model.User;

public interface UserPort {

    void register(User user);

    boolean userExists(String email);
}
