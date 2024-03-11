package com.example.demo.cleanarchi.domain.usecases.inscription.interactors;

import com.example.demo.cleanarchi.domain.model.Utilisateur;
import com.example.demo.cleanarchi.domain.usecases.UtilisateurPort;
import org.springframework.stereotype.Component;

@Component
public class VerificationPreInscription {

    private final UtilisateurPort utilisateurPort;

    public VerificationPreInscription(UtilisateurPort utilisateurPort) {
        this.utilisateurPort = utilisateurPort;
    }

    public boolean utilisateurDejaInscrit(Utilisateur utilisateur) {
        return true;
    }
}
