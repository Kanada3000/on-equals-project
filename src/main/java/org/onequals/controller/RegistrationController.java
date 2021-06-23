package org.onequals.controller;

import org.onequals.domain.User;
import org.onequals.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registration(){
        return "sign-in";
    }

    @PostMapping("/register")
    public String addUser(Model model,
                          @RequestParam("password1") String pas1,
                          @RequestParam("password2") String pas2,
                          @RequestParam("name") String name,
                          @RequestParam("username") String username){
        User userFromDb = userService.findUser(username);

        if (userFromDb != null){
            model.addAttribute("message1", "Такий користувач вже існує!");
            return "sign-in";
        }

        User user = new User();

        user.setName(name);
        user.setUsername(username);

        if (pas1.equals(pas2)){
            user.setPassword(userService.setPassword(pas1));
        } else{
            model.addAttribute("message2", "Паролі не співпадають!");
            return "sign-in";
        }
        user.setLink(userService.getRandomLink());
        userService.save(user);
        model.addAttribute("link","/register/activate/" + user.getLink());
        return "sign-in-3";
    }

    @GetMapping("/register/activate/{link}")
    public String activate(@PathVariable("link") String link){
        User user = userService.getByLink(link);
        user.setActivated(true);
        userService.save(user);
        return "redirect:/sign-in-2/" + user.getId();
    }

    @GetMapping("/sign-in-2/{id}")
    public String registerContinue(@PathVariable("id")Long id ,Model model){
        model.addAttribute("id", id);
        return "sign-in-2";
    }
}