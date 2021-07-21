package org.onequals.controller;

import org.onequals.domain.Role;
import org.onequals.domain.Seeker;
import org.onequals.domain.User;
import org.onequals.services.CategoryService;
import org.onequals.services.CityService;
import org.onequals.services.SeekerService;
import org.onequals.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/seeker")
public class SeekerController {
    private final CityService cityService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final SeekerService seekerService;

    public SeekerController(CityService cityService, CategoryService categoryService, UserService userService, SeekerService seekerService) {
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.seekerService = seekerService;
    }

    @GetMapping()
    public String indexPage(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "reg-finder";
    }

    @PostMapping()
    public String createSeeker(Principal principal,
                               @RequestParam String name,
                               @RequestParam String category,
                               @RequestParam String email,
                               @RequestParam List<String> citString,
                               @RequestParam String site,
                               @RequestParam String linkFacebook,
                               @RequestParam String linkInstagram,
                               @RequestParam String linkTwitter,
                               @RequestParam String linkLinkedIn,
                               @RequestParam String description){
        User user = userService.findUser(principal.getName());
        Seeker seeker = new Seeker();
        seeker.setUser(user);
        seeker.setName(name);
        seeker.setCategory(categoryService.findByName(category));
        seeker.setEmail(email);
        cityService.addCities(seeker, cityService.findByNames(citString));
        seeker.setSite(site);
        seeker.setLinkFacebook(linkFacebook);
        seeker.setLinkInstagram(linkInstagram);
        seeker.setLinkTwitter(linkTwitter);
        seeker.setLinkLinkedIn(linkLinkedIn);
        seeker.setDescription(description);
        seekerService.save(seeker);

        Set<Role> roles = user.getRoles();
        roles.add(Role.SEEKER);
        user.setRoles(roles);

        user.setLink(null);
        userService.updateRole(Role.SEEKER);
        user.setName(name);
        userService.save(user);



        return "redirect:/resume/new";
    }
}