package org.onequals.controller;

import org.onequals.domain.Resume;
import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
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
    private final StorageService storageService;

    public UserController(UserService userService, EmployerService employerService, SeekerService seekerService, VacancyService vacancyService, ResumeService resumeService, StorageService storageService) {
        this.userService = userService;
        this.employerService = employerService;
        this.seekerService = seekerService;
        this.vacancyService = vacancyService;
        this.resumeService = resumeService;
        this.storageService = storageService;
    }


    @GetMapping
    public String profilePage(Principal principal, Model model) {
        User user = userService.findUser(principal.getName());
        if (!user.getRoles().contains(Role.EMPLOYER) && !user.getRoles().contains(Role.SEEKER)) {
            return "redirect:/choose";
        }
        if (user.getRoles().contains(Role.EMPLOYER)) {
            model.addAttribute("user", employerService.findByUser(user));
            model.addAttribute("likes", user.getLikedResume());
            model.addAttribute("block", vacancyService.findByUser(user));
        } else if (user.getRoles().contains(Role.SEEKER)) {
            model.addAttribute("user", seekerService.findByUser(user));
            model.addAttribute("likes", user.getLikedVacancy());
            model.addAttribute("block", resumeService.findByUser(user));
            model.addAttribute("map", storageService.findFiles(user, true));
        }
        return "profile";
    }

    @GetMapping("/delete")
    public String profileDelete(Principal principal) {
        User user = userService.findUser(principal.getName());
        userService.delete(user.getId());
        userService.clearSession();
        return "redirect:/";
    }

    @GetMapping("/delete/vacancy/{id}")
    public String deleteVacancy(@PathVariable Long id) {
        vacancyService.delete(id);
        return "redirect:/profile";
    }

    @GetMapping("/delete/resume/{id}")
    public String deleteResume(@PathVariable Long id) {
        resumeService.delete(id);
        return "redirect:/profile";
    }

    @PostMapping("/deleteLike")
    public String deleteLike(Principal principal,
                             @RequestParam("likeId") List<String> id,
                             @RequestParam(required = false) Long userId) {
        User user = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            userService.deleteResumeLikes(user, id);
        } else if (user.getRoles().contains(Role.SEEKER)) {
            userService.deleteVacancyLikes(user, id);
        }
        if (userId != null) {
            return "redirect:/profile/user/" + userId;
        } else
            return "redirect:/profile";
    }

    @PostMapping("/addLike")
    public String addLike(Principal principal,
                          @RequestParam("likeId") List<String> id,
                          @RequestParam(required = false) Long userId) {
        User user = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            userService.addResumeLikes(user, id);
        } else if (user.getRoles().contains(Role.SEEKER)) {
            userService.addVacancyLikes(user, id);
        }
        if (userId != null) {
            return "redirect:/profile/user/" + userId;
        } else
            return "redirect:/profile";
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestParam String path) {
        storageService.deleteFile(path);
        return "redirect:/profile";
    }

    @GetMapping("/hide")
    public String hideUser(Principal principal) {
        User user = userService.findUser(principal.getName());
        user.setHidden(!user.getHidden());
        userService.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/user/{id}")
    public String profileUser(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findById(id).get();
        User currentUser = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            model.addAttribute("user", employerService.findByUser(user));
            model.addAttribute("block", vacancyService.findByUser(user));
        } else if (user.getRoles().contains(Role.SEEKER)) {
            model.addAttribute("user", seekerService.findByUser(user));
            model.addAttribute("block", resumeService.findByUser(user));
            model.addAttribute("map", storageService.findFiles(user, true));
        }
        if (currentUser.getRoles().contains(Role.EMPLOYER)) {
            if (currentUser.getLikedResume() != null)
                if (!currentUser.getLikedResume().isEmpty()) {
                    StringBuilder s = new StringBuilder();
                    for (Resume r : currentUser.getLikedResume()) {
                        s.append(r.getId()).append("&");
                    }
                    model.addAttribute("likesId", s);
                }
        } else if (currentUser.getRoles().contains(Role.SEEKER)) {
            if (currentUser.getLikedVacancy() != null)
                if (!currentUser.getLikedVacancy().isEmpty()) {
                    StringBuilder s = new StringBuilder();
                    for (Vacancy v : currentUser.getLikedVacancy()) {
                        s.append(v.getId()).append("&");
                    }
                    model.addAttribute("likesId", s);
                }
        }
        return "profile-guest";
    }
}