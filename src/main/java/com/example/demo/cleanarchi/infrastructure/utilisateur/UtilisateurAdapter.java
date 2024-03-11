package com.example.demo.cleanarchi.infrastructure.utilisateur;

import com.example.demo.cleanarchi.domain.model.Utilisateur;
import com.example.demo.cleanarchi.domain.usecases.UtilisateurPort;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurAdapter implements UtilisateurPort {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurAdapter(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public void enregistrer(Utilisateur utilisateur) {
        utilisateurRepository.save(UtilisateurMapper.map(utilisateur));
    }

    @Override
    public boolean utilisateurExiste(String email) {
        return utilisateurRepository.findByEmail(email).isPresent();
    }
}
