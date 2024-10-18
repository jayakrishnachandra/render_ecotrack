package com.example.EcoTrack;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    Optional<Token> findByEmail(String email);
    Optional<Token> findByToken(String tokenString);
}

