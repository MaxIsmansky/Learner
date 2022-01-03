package com.learner.project.controllers;

import com.learner.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ismansky Maxim
 * Date: 03.01.2022
 */
@Controller
public class SearchController {

    private UserRepository userRepository;

    @Autowired
    public SearchController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/searchprofile")
    public String findUserProfile(@RequestParam Long id) {
        return String.format("redirect:/%s/main", id);
    }
}
