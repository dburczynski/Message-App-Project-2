package com.jee.messagingapp2.repository;

import com.jee.messagingapp2.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    Post findByUser(String user);
    Post findById(long id);
}
