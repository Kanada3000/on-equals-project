package org.onequals.controller;

import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/profile")
@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;
    private final EmployerService employerService;
    private final SeekerService seekerService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    public UserController(UserService userService, EmployerService employerService, SeekerService seekerService, VacancyService vacancyService, ResumeService resumeService) {
        this.userService = userService;
        this.employerService = employerService;
        this.seekerService = seekerService;
        this.vacancyService = vacancyService;
        this.resumeService = resumeService;
    }


    @GetMapping
    public String profilePage(Principal principal, Model model){
        User user = userService.findUser(principal.getName());
        if(!user.getRoles().contains(Role.EMPLOYER) && !user.getRoles().contains(Role.SEEKER)){
            return "redirect:/choose";
        }
        if (user.getRoles().contains(Role.EMPLOYER)){
            model.addAttribute("user", employerService.findByUser(user));
            model.addAttribute("likes", user.getLikedResume());
            model.addAttribute("block", vacancyService.findByUser(user));
        } else if (user.getRoles().contains(Role.SEEKER)){
            model.addAttribute("user", seekerService.findByUser(user));
            model.addAttribute("likes", user.getLikedVacancy());
            model.addAttribute("block", resumeService.findByUser(user));
        }
        return "profile";
    }

    @GetMapping("/delete")
    public String profileDelete (Principal principal){
        User user = userService.findUser(principal.getName());
        userService.delete(user.getId());
        userService.clearSession();
        return "redirect:/";
    }

    @GetMapping("/delete/vacancy/{id}")
    public String deleteVacancy(@PathVariable Long id){
        vacancyService.delete(id);
        return "redirect:/profile";
    }

    @GetMapping("/delete/resume/{id}")
    public String deleteResume(@PathVariable Long id){
        resumeService.delete(id);
        return "redirect:/profile";
    }

    @PostMapping("/deleteLike")
    public String deleteLike(Principal principal, @RequestParam("likeId") List<String> id){
        User user = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)){
            userService.deleteResumeLikes(user, id);
        } else if (user.getRoles().contains(Role.SEEKER)){
            userService.deleteVacancyLikes(user, id);
        }
        return "redirect:/profile";
    }
}