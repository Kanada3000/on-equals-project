package org.onequals.controller;

import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        User userFromDb = userRepo.findByUsername(username);

        if (userFromDb != null){
            model.addAttribute("message1", "Такий користувач вже існує!");
            return "sign-in";
        }

        User user = new User();

        user.setRoles(Collections.singleton(Role.USER));
        user.setName(name);
        user.setUsername(username);

        if (pas1.equals(pas2)){
            user.setPassword(passwordEncoder.encode(pas1));
        } else{
            model.addAttribute("message2", "Паролі не співпадають!");
            return "sign-in";
        }
        userRepo.save(user);
        return "redirect:/log-in";
    }

}