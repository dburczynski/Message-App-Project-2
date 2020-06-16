package com.jee.messagingapp2.repository;

import com.jee.messagingapp2.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByName(String name);
}
