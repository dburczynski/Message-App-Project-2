package com.jee.messagingapp2.controllers.api;

import com.jee.messagingapp2.domain.Post;
import com.jee.messagingapp2.domain.User;
import com.jee.messagingapp2.repository.*;
import com.jee.messagingapp2.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/post")
public class PostRestController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReactivePostRepository reactivePostRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    SequenceGeneratorService sq;

    @GetMapping("")
    public Mono<ResponseEntity<List<Post>>> allPosts() {

        return reactivePostRepository.findAll()
                .collectList()
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null,HttpStatus.NOT_FOUND)));
    }

//    @GetMapping("")
//    public Mono<ResponseEntity<List<Post>>> userPosts(@RequestParam("user") String email) {
//
//        return reactivePostRepository.findAllByUser(email)
//                .collectList()
//                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
//                .switchIfEmpty(Mono.just(new ResponseEntity<>(null,HttpStatus.NOT_FOUND)));
//    }
    @PutMapping("")
    public Mono<ResponseEntity<Post>> createPost(@RequestParam("email") String email,
                                                 @RequestParam("message") String message)

    {
        Post post = new Post();
        post.setId(sq.generateSequence(Post.SEQUENCE_NAME));
        post.setMessage(message);
        post.setUser(email);

        return reactivePostRepository.save(post)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK));
    }

    @DeleteMapping("")
    public Mono<ResponseEntity<List<Post>>> deletePost(@RequestParam("id") long id) {
        Post post = postRepository.findById(id);
        if(post != null) {
            postRepository.delete(post);
        }
        return reactivePostRepository.findAll()
                .collectList()
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null,HttpStatus.NOT_FOUND)));
    }


}
