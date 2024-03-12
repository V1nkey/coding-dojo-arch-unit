package com.example.demo.cleanarchitecture.domain.usecases.user;

import com.example.demo.cleanarchitecture.domain.model.User;
import com.example.demo.cleanarchitecture.domain.usecases.UserPort;
import com.example.demo.cleanarchitecture.domain.usecases.user.interactors.CheckRegistration;
import org.springframework.stereotype.Component;

@Component
public class RegisterUser {

    private final UserPort userPort;
    private final CheckRegistration checkRegistration;

    public RegisterUser(UserPort userPort, CheckRegistration checkRegistration) {
        this.userPort = userPort;
        this.checkRegistration = checkRegistration;
    }

    public void execute(User user) {
        if (checkRegistration.userLivesInCity(user)) {
            userPort.registerUser(user);
        }
    }

}
