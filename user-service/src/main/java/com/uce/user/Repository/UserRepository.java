package com.uce.user.Repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uce.user.Model.User;

@Repository
public interface UserRepository extends MongoRepository<User,ObjectId>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
