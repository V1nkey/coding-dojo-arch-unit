package com.example.demo.cleanarchi.infrastructure.utilisateur;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends MongoRepository<UtilisateurDatabase, String> {

    Optional<UtilisateurDatabase> findByEmail(String email);
}
