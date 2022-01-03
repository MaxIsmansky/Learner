package com.learner.project.controllers;

import com.learner.project.models.User;
import com.learner.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ismansky Maxim 17.12.2021
 */
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private UserRepository userRepository;

    @Autowired
    public UserApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAnyRole()")
    @GetMapping("/all")
    public Iterable<User> getUsersApi() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }
}
