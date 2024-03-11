package com.example.demo.cleanarchi.domain.usecases;

import com.example.demo.cleanarchi.domain.model.Utilisateur;

public interface UtilisateurPort {

    void enregistrer(Utilisateur utilisateur);

    boolean utilisateurExiste(String email);
}
