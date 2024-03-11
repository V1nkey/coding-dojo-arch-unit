package com.example.demo.cleanarchi.domain.model;

public class Utilisateur {

    private String id;
    private String nom;
    private String prenom;
    private String email;

    public Utilisateur(String nom, String prenom, String email) {
        this(null, nom, prenom, email);
    }

    public Utilisateur(String id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }
}
