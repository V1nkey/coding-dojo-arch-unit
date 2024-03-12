package com.example.demo.cleanarchi.domain.usecases.registration.interactors;

import com.example.demo.cleanarchi.domain.model.User;
import com.example.demo.cleanarchi.domain.usecases.UserPort;
import org.springframework.stereotype.Component;

@Component
public class RegistrationControl {

    private final UserPort userPort;

    public RegistrationControl(UserPort userPort) {
        this.userPort = userPort;
    }

    public boolean userIsAlreadyRegistered(User user) {
        return userPort.userExists(user.getEmailAddress());
    }
}
