package com.learner.project.controllers;

import com.learner.project.models.User;
import com.learner.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MainController {

    private static Long id;

    private UserRepository userRepository;

    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getid")
    public String getId(@AuthenticationPrincipal User user) {
        Long id = user.getId();
        return String.format("redirect:/%d/main", id);
    }

    @GetMapping("/{id}/main")
    public String mainPage(@AuthenticationPrincipal User user,
                           @PathVariable("id") Long id, Model model) {
        this.id = id;
        model.addAttribute("id", id);
        User userFromDb = userRepository.findById(id).get();

        String userPhoto = userFromDb.getUserPhotoName() == null ? "#" : userFromDb.getUserPhotoName();
        model.addAttribute("userPhoto", userPhoto);

        String[] infoArray = userFromDb.getInfo() == null ? new String[] {"Здесь будет отображаться информация о вас"} : userFromDb.getInfo().split("\n");
        String[] skillsArray = userFromDb.getSkills() == null ? new String[] {"Здесь будут ваши умения"} : userFromDb.getSkills().split("\n");
        String[] plansArray = userFromDb.getPlans() == null ? new String[] {"А здесь ваши планы"} : userFromDb.getPlans().split("\n");
        model.addAttribute("infoArray", infoArray);
        model.addAttribute("skillsArray",skillsArray);
        model.addAttribute("plansArray", plansArray);
        model.addAttribute("user", userFromDb);
        return "mainPage";
    }

    @GetMapping("/main")
    public String mainRed() {
        Long id = MainController.id;
        return String.format("redirect:/%d/main", id);
    }

    @GetMapping("/search")
    public String findUser(String username) {
        return null;
    }


}
