package org.onequals.controller;

import org.onequals.domain.Seeker;
import org.onequals.domain.User;
import org.onequals.domain.UserType;
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
import java.util.Collections;

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
    public String indexPage(Principal principal, Model model, @RequestParam("id") Long id) {
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("id", id);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "reg-finder";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping()
    public String createSeeker(@RequestParam Long id,
                                 @RequestParam String name,
                                 @RequestParam String category,
                                 @RequestParam String email,
                                 @RequestParam String citString,
                                 @RequestParam String site,
                                 @RequestParam String linkFacebook,
                                 @RequestParam String linkInstagram,
                                 @RequestParam String linkTwitter,
                                 @RequestParam String linkLinkedIn,
                                 @RequestParam String description){
        User user = userService.findById(id).get();
        Seeker seeker = new Seeker();
        seeker.setUser(user);
        seeker.setName(name);
        seeker.setCategory(categoryService.findByName(category));
        seeker.setEmail(email);
        seeker.setCity(cityService.findByName(citString));
        seeker.setSite(site);
        seeker.setLinkFacebook(linkFacebook);
        seeker.setLinkInstagram(linkInstagram);
        seeker.setLinkTwitter(linkTwitter);
        seeker.setLinkLinkedIn(linkLinkedIn);
        seeker.setDescription(description);
        seekerService.save(seeker);

        user.setLink(null);
        userService.save(user);

        return "redirect:/resume/new";
    }
}