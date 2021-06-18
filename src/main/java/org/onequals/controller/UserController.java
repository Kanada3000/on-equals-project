package org.onequals.controller;

import org.onequals.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.onequals.repo.UserRepo;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/user")
@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String greeting(Principal principal, Model model){
        String name = userRepo.findByUsername(principal.getName()).getName();
        model.addAttribute("name", name);
        return "index";
    }
}