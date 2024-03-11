package com.example.demo.cleanarchi.infrastructure.utilisateur;

import com.example.demo.cleanarchi.domain.model.Utilisateur;

public class UtilisateurMapper {
    private UtilisateurMapper() {
    }

    public static UtilisateurDatabase map(Utilisateur utilisateur) {
        return new UtilisateurDatabase(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail());
    }
}
