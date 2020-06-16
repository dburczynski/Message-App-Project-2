package com.jee.messagingapp2.repository;

import com.jee.messagingapp2.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}