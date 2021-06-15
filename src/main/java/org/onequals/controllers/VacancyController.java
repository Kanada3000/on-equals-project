package org.onequals.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vacancies")
@Controller
public class VacancyController {

    @GetMapping("/add")
    public String vacancyPage() {
        return "vacancy";
    }


    @GetMapping("/list")
    public String vacanciesPage() {
        return "search-list";
    }

}
