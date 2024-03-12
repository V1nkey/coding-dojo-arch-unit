package com.example.demo.cleanarchi.domain.usecases.registration;

import com.example.demo.cleanarchi.domain.model.User;
import com.example.demo.cleanarchi.domain.usecases.UserPort;
import com.example.demo.cleanarchi.domain.usecases.registration.interactors.RegistrationControl;
import org.springframework.stereotype.Component;

@Component
public class RegisterUser {

    private final RegistrationControl verificationPreInscription;
    private final UserPort userPort;

    public RegisterUser(RegistrationControl verificationPreInscription, UserPort userPort) {
        this.verificationPreInscription = verificationPreInscription;
        this.userPort = userPort;
    }

    public void execute(User user) throws Exception {
        if (verificationPreInscription.userIsAlreadyRegistered(user)) {
            throw new Exception("Utilisateur déjà inscrit");
        }

        userPort.register(user);
    }
}
