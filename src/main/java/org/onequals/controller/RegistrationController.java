package org.onequals.controller;

import org.onequals.domain.User;
import org.onequals.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registration() {
        return "sign-in";
    }

    @PostMapping("/register")
    public String addUser(Model model,
                          @RequestParam("password") String pas,
                          @RequestParam("name") String name,
                          @RequestParam("username") String username) {
        User userFromDb = userService.findUser(username);

        if (userFromDb != null) {
            model.addAttribute("message", "Такий користувач вже існує!");
            return "sign-in";
        }

        User user = new User();

        user.setName(name);
        user.setUsername(username);
        user.setPassword(userService.setPassword(pas));
        user.setLink(userService.getRandomLink());
        userService.save(user);

        model.addAttribute("link", "/register/activate/" + user.getLink());
        return "sign-in-3";
    }

    @GetMapping("/choose")
    public String choose(Principal principal, Model model) {
        User user = userService.findUser(principal.getName());
        model.addAttribute("link", "/register/activate/" + user.getLink());
        return "sign-in-3";
    }

    @GetMapping("/register/activate/{link}")
    public String activate(@PathVariable("link") String link) {
        User user = userService.getByLink(link);
        user.setActivated(true);
        userService.save(user);
        userService.auth(user);
        return "redirect:/sign-in-2/";
    }

    @GetMapping("/sign-in-2")
    public String registerContinue(Model model) {
        return "sign-in-2";
    }
}