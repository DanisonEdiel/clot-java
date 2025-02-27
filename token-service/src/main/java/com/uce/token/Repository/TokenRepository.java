package com.uce.token.Repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uce.token.Model.Token;

@Repository
public interface TokenRepository extends MongoRepository<Token, ObjectId> {
    Optional<Token> findByToken(String token);
    Token findTokenByToken(String token);
    
}
