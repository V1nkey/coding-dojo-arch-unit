package com.example.demo.cleanarchi.infrastructure.utilisateur;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class UtilisateurDatabase {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateInscription;

    public UtilisateurDatabase(String id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateInscription = LocalDate.now();
    }
}
