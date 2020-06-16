package com.jee.messagingapp2.repository;

import com.jee.messagingapp2.domain.Post;
import com.jee.messagingapp2.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReactivePostRepository extends ReactiveMongoRepository<Post, String> {

    Flux<Post> findAllByUser(String user);
}
