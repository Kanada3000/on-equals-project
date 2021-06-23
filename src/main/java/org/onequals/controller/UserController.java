package org.onequals.controller;

import org.onequals.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/user")
@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String greeting(Principal principal, Model model){
        String name = userService.findUser(principal.getName()).getName();
        model.addAttribute("name", name);
        return "index";
    }
}