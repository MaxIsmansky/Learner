package com.learner.project.controllers;

import com.learner.project.models.Role;
import com.learner.project.models.User;
import com.learner.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    private UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String registerPage() {
        return "registrationPage";
    }

    @GetMapping("/enter")
    public String enter() {
        return "registrationFormPage";
    }

    @GetMapping("/create")
    public String createAccount() {
        return "createAccountPage";
    }

    @PostMapping("/create")
    public String createAccountData(User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            String message = String.format("Пользователь %s уже существует", user.getUsername());
            model.addAttribute("message", message);
            return "createAccountPage";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/enter";
    }
}
