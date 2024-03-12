package com.example.demo.cleanarchitecture.domain.usecases.user.interactors;

import com.example.demo.cleanarchitecture.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class CheckRegistration {

    public boolean userLivesInCity(User user) {
        return true;
    }
}
