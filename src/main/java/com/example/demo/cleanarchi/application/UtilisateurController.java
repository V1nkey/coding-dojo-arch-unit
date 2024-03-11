package com.example.demo.cleanarchi.application;

import com.example.demo.cleanarchi.domain.usecases.inscription.InscrireUtilisateur;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {

    private final InscrireUtilisateur inscrireUtilisateur;

    public UtilisateurController(InscrireUtilisateur inscrireUtilisateur) {
        this.inscrireUtilisateur = inscrireUtilisateur;
    }

    @PostMapping("/inscription")
    public ResponseEntity<Void> inscrireUtilisateur(@RequestBody UtilisateurRequest utilisateur) {
        try {
            inscrireUtilisateur.executer(UtilisateurMapper.map(utilisateur));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().build();
    }
}
