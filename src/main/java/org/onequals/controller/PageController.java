package org.onequals.controller;

import org.onequals.domain.User;
import org.onequals.services.CategoryService;
import org.onequals.services.CityService;
import org.onequals.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final CityService cityService;

    public PageController(UserService userService, CategoryService categoryService, CityService cityService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public String indexPage(Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        return "index";
    }

    @GetMapping("/log-in")
    public String signInPage(Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        return "log-in";
    }
}