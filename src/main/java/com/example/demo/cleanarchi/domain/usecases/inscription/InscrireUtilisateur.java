package com.example.demo.cleanarchi.domain.usecases.inscription;

import com.example.demo.cleanarchi.domain.model.Utilisateur;
import com.example.demo.cleanarchi.domain.usecases.UtilisateurPort;
import com.example.demo.cleanarchi.domain.usecases.inscription.interactors.VerificationPreInscription;
import org.springframework.stereotype.Component;

@Component
public class InscrireUtilisateur {

    private final VerificationPreInscription verificationPreInscription;
    private final UtilisateurPort utilisateurPort;

    public InscrireUtilisateur(VerificationPreInscription verificationPreInscription, UtilisateurPort utilisateurPort) {
        this.verificationPreInscription = verificationPreInscription;
        this.utilisateurPort = utilisateurPort;
    }

    public void executer(Utilisateur utilisateur) throws Exception {
        if (verificationPreInscription.utilisateurDejaInscrit(utilisateur)) {
            throw new Exception("Utilisateur déjà inscrit");
        }

        utilisateurPort.enregistrer(utilisateur);
    }
}
