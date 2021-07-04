package org.onequals.controller;

import org.onequals.domain.Role;
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
    public String indexPage(Model model) {
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        return "index";
    }

    @GetMapping("/log-in")
    public String signInPage(Model model) {
        return "log-in";
    }

    @GetMapping("/list")
    public String toList(Principal principal) {
        if (principal != null) {
            User user = userService.findUser(principal.getName());
            if (user.getRoles().contains(Role.EMPLOYER)) {
                return "redirect:/resume/list";
            } else {
                return "redirect:/vacancy/list";
            }
        }
        return "redirect:/vacancy/list";
    }

    @GetMapping("/trick/admin/activated/true")
    public String activatedAdmin(){
        userService.activatedAdmin();
        return "redirect:/";
    }
}