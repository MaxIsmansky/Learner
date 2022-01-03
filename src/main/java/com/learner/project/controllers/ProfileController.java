package com.learner.project.controllers;

import com.learner.project.models.User;
import com.learner.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Value("${upload.path.photo}")
    private String uploadPath;

    private UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User user,
                          Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        String[] infoArray = userFromDb.getInfo() == null ? new String[] {""} : userFromDb.getInfo().split("\n");
        String[] skillsArray = userFromDb.getSkills() == null ? new String[] {""} : userFromDb.getSkills().split("\n");
        String[] plansArray = userFromDb.getPlans() == null ? new String[] {""} : userFromDb.getPlans().split("\n");
        String userPhoto = userFromDb.getUserPhotoName() == null ? "#" : userFromDb.getUserPhotoName();
        model.addAttribute("infoArray", infoArray);
        model.addAttribute("skillsArray",skillsArray);
        model.addAttribute("plansArray", plansArray);
        model.addAttribute("user", userFromDb);
        model.addAttribute("userPhoto", userPhoto);
        return "profilePage";
    }

    @PostMapping("/addinfo")
    public String profileAddInfo(@AuthenticationPrincipal User user,
                                 @RequestParam(required = false) String info,
                                 @RequestParam(required = false) String skills,
                                 @RequestParam(required = false) String plans,
                                 @RequestParam(required = false) MultipartFile userphoto) throws IOException {

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (info != null) {
            userFromDb.setInfo(info);
            userRepository.save(userFromDb);
            return "redirect:/profile";
        }

        if (skills != null) {
            userFromDb.setSkills(skills);
            userRepository.save(userFromDb);
            return "redirect:/profile";
        }

        if (plans != null) {
            userFromDb.setPlans(plans);
            userRepository.save(userFromDb);
            return "redirect:/profile";
        }

        if (userphoto != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + userphoto.getOriginalFilename();

            userphoto.transferTo(new File(uploadPath + "/" + resultFileName));

            userFromDb.setUserPhotoName(resultFileName);
            userRepository.save(userFromDb);
        }

        return "redirect:/profile";
    }
}
