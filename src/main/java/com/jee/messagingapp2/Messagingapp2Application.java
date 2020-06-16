package com.jee.messagingapp2;

import com.jee.messagingapp2.domain.Post;
import com.jee.messagingapp2.domain.Role;
import com.jee.messagingapp2.domain.User;
import com.jee.messagingapp2.repository.PostRepository;
import com.jee.messagingapp2.repository.RoleRepository;
import com.jee.messagingapp2.repository.UserRepository;
import com.jee.messagingapp2.service.SequenceGeneratorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Messagingapp2Application {

    public static void main(String[] args) {
        SpringApplication.run(Messagingapp2Application.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, PostRepository postRepository, BCryptPasswordEncoder encoder, SequenceGeneratorService sq) {

        return args -> {

            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setId(sq.generateSequence(Role.SEQUENCE_NAME));
                newAdminRole.setName("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setId(sq.generateSequence(Role.SEQUENCE_NAME));
                newUserRole.setName("USER");
                roleRepository.save(newUserRole);
            }

            User admin = userRepository.findByEmail("admin@admin.com");
            if(admin == null) {
                User newAdmin = new User();
                newAdmin.setId(sq.generateSequence(User.SEQUENCE_NAME));
                newAdmin.setUsername("Admin");
                newAdmin.setEmail("admin@admin.com");

                newAdmin.setPassword(encoder.encode("12345678"));
                newAdmin.setRole("ADMIN");
                userRepository.save(newAdmin);
            }

            User user = userRepository.findByEmail("user@user.com");
            if(user == null) {
                User newUser = new User();
                newUser.setId(sq.generateSequence(User.SEQUENCE_NAME));
                newUser.setUsername("User");
                newUser.setEmail("user@user.com");
                newUser.setPassword(encoder.encode("12345678"));
                newUser.setRole("USER");
                userRepository.save(newUser);
            }

            Post post1 = new Post();
            post1.setId(sq.generateSequence(Post.SEQUENCE_NAME));
            post1.setMessage("Hello there");
            post1.setUser("admin@admin.com");
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setId(sq.generateSequence(Post.SEQUENCE_NAME));
            post2.setMessage("Welcome");
            post2.setUser("user@user.com");
            postRepository.save(post2);

        };

    }


}
