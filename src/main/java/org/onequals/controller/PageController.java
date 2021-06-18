package org.onequals.controller;

import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/log-in")
    public String signInPage() {
        return "log-in";
    }

    @GetMapping("/sign-in2")
    public String signIn2Page() {
        return "sign-in-2";
    }

    @GetMapping("/sign-in3")
    public String signIn3Page() {
        return "sign-in-3";
    }

    @GetMapping("/reg-finder")
    public String regFinderPage() {
        return "reg-finder";
    }

    @GetMapping("/reg-employer")
    public String regEmpPage() {
        return "reg-employer";
    }

    @GetMapping("/resume")
    public String resumePage() {
        return "resume-finder";
    }
}