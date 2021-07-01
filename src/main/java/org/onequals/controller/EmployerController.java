package org.onequals.controller;

import org.onequals.domain.Employer;
import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.services.CategoryService;
import org.onequals.services.CityService;
import org.onequals.services.EmployerService;
import org.onequals.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/employer")
public class EmployerController {
    private final CityService cityService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final EmployerService employerService;

    public EmployerController(CityService cityService, CategoryService categoryService, UserService userService, EmployerService employerService) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.employerService = employerService;
    }

    @GetMapping()
    public String indexPage(Model model, @RequestParam("id") Long id) {
        model.addAttribute("id", id);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "reg-employer";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping()
    public String createEmployer(@RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam String category,
                                 @RequestParam String email,
                                 @RequestParam String citString,
                                 @RequestParam String site,
                                 @RequestParam String linkFacebook,
                                 @RequestParam String linkInstagram,
                                 @RequestParam String linkTwitter,
                                 @RequestParam String linkLinkedIn,
                                 @RequestParam int age,
                                 @RequestParam int quantity,
                                 @RequestParam int size,
                                 @RequestParam String description){
        User user = userService.findById(id).get();
        Employer employer = new Employer();
        employer.setUser(user);
        employer.setName(name);
        employer.setCategory(categoryService.findByName(category));
        employer.setEmail(email);
        employer.setCity(cityService.findByName(citString));
        employer.setSite(site);
        employer.setLinkFacebook(linkFacebook);
        employer.setLinkInstagram(linkInstagram);
        employer.setLinkTwitter(linkTwitter);
        employer.setLinkLinkedIn(linkLinkedIn);
        employer.setAge(age);
        employer.setEmpCount(quantity);
        employer.setSize(size);
        employer.setDescription(description);
        employerService.save(employer);

        Set<Role> roles = user.getRoles();
        roles.add(Role.EMPLOYER);
        user.setRoles(roles);

        user.setLink(null);
        userService.save(user);

        return "redirect:/vacancy/new";
    }
}