package org.onequals.controller;

import org.onequals.domain.*;
import org.onequals.repo.CategoryRepo;
import org.onequals.repo.TypeRepo;
import org.onequals.repo.UserRepo;
import org.onequals.repo.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private TypeRepo typeRepo;

    @Autowired
    private VacancyRepo vacancyRepo;

    @GetMapping("/list")
    public String vacancyList(Model model,
                              @RequestParam(name = "sort", defaultValue = "id")  String sort){
        List<Category> categories = categoryRepo.findAll();
        List<Vacancy> vacancies = vacancyRepo.findAll(Sort.by(sort));
        model.addAttribute("categories", categories);
        model.addAttribute("total", vacancyRepo.countVacancy());
        model.addAttribute("type", typeRepo.findAll());
        model.addAttribute("vacancies", vacancies);
        return "search-list";
    }


    @GetMapping("/new")
    public String addVacancy(Model model){
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        return "vacancy";
    }

    @PostMapping("/new")
    public String addVacancy(Principal principal,
                             @RequestParam("category") String cat,
                             @RequestParam("type") String t,
                             @RequestParam("country") String country,
                             @RequestParam("city") String city,
                             @RequestParam("description") String desc){
        User user = userRepo.findByUsername(principal.getName());
        Vacancy vacancy = new Vacancy();
        vacancy.setUser(user);
        vacancy.setCity(city);
        vacancy.setCountry(country);
        vacancy.setDescription(desc);
        Category category = categoryRepo.findCategoryByLong(cat);
        Type type = typeRepo.findTypeByName(t);
        category.setTotal(category.getTotal()+1);
        type.setTotal(type.getTotal()+1);
        vacancy.setType(type);
        vacancy.setCategory(category);
        vacancyRepo.save(vacancy);
        categoryRepo.save(category);
        typeRepo.save(type);
        return "redirect:/";
    }

}