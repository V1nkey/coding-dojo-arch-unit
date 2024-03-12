package com.example.demo.cleanarchi.infrastructure.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends MongoRepository<UserDatabase, String> {

    Optional<UserDatabase> findByEmail(String email);
}
