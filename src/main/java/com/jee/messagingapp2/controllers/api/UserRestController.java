package com.jee.messagingapp2.controllers.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.jee.messagingapp2.domain.Post;
import com.jee.messagingapp2.domain.User;
import com.jee.messagingapp2.repository.PostRepository;
import com.jee.messagingapp2.repository.ReactiveUserRepository;
import com.jee.messagingapp2.repository.RoleRepository;
import com.jee.messagingapp2.repository.UserRepository;
import com.jee.messagingapp2.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.notFound;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReactiveUserRepository reactiveUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    SequenceGeneratorService sq;

    @GetMapping("")
    public Mono<ResponseEntity<List<User>>> allUsers() {

        return reactiveUserRepository.findAll()
                .collectList()
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null,HttpStatus.NOT_FOUND)));
    }

    @PutMapping("")
    public Mono<ResponseEntity<User>> createUser(@RequestParam("email") String email,
                                                 @RequestParam("username") String username,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("role") String role)
    {
        User user = new User();
        user.setId(sq.generateSequence(User.SEQUENCE_NAME));
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(role.toUpperCase());

        return reactiveUserRepository.save(user)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK));
    }



    @DeleteMapping("")
    public Mono<ResponseEntity<List<User>>> deleteUser(@RequestParam("email") String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            userRepository.delete(user);
        }
        return reactiveUserRepository.findAll()
                .collectList()
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null,HttpStatus.NOT_FOUND)));
    }

    @PostMapping("")
    public Mono<ResponseEntity<User>> updateUser(@RequestParam("email") String email,
                                                       @RequestParam("username") String username,
                                                       @RequestParam("password") String password)
    {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setUsername(username);
        }

        return reactiveUserRepository.save(user)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK));
    }
}
