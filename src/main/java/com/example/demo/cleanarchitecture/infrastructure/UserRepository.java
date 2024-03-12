package com.example.demo.cleanarchitecture.infrastructure;

import com.example.demo.cleanarchitecture.application.UserRequest;
import com.example.demo.cleanarchitecture.domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {}
