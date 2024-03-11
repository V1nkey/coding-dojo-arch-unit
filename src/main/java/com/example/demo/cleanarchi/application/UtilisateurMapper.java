package com.example.demo.cleanarchi.application;

import com.example.demo.cleanarchi.domain.model.Utilisateur;

public class UtilisateurMapper {

    private UtilisateurMapper() {
    }

    public static Utilisateur map(UtilisateurRequest utilisateurRequest) {
        return new Utilisateur(utilisateurRequest.nom(), utilisateurRequest.prenom(), utilisateurRequest.email());
    }
}
