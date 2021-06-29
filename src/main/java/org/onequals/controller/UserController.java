package org.onequals.controller;

import org.onequals.domain.User;
import org.onequals.services.UserService;
import org.onequals.services.VacancyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/profile")
@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;
    private final VacancyService vacancyService;

    public UserController(UserService userService, VacancyService vacancyService) {
        this.userService = userService;
        this.vacancyService = vacancyService;
    }


    @GetMapping
    public String profilePage(Principal principal, Model model){
        if(principal != null) {
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        return "profile";
    }
}