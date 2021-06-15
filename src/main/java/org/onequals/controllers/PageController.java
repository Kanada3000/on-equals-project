package org.onequals.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "sign-in";
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
