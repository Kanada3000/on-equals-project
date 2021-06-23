package org.onequals.controller;

import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    private final CategoryService categoryService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;

    public VacancyController(CategoryService categoryService, VacancyService vacancyService, UserService userService, TypeService typeService, CityService cityService) {
        this.categoryService = categoryService;
        this.vacancyService = vacancyService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
    }

    @GetMapping("/list")
    public String vacancyList(Principal principal, Model model,
                              @RequestParam(name = "sort", defaultValue = "id")  String sort,
                              @RequestParam(name = "min", required = false)  Integer min,
                              @RequestParam(name = "max", required = false)  Integer max,
                              @RequestParam(name = "category", required = false)  List<Long> category,
                              @RequestParam(name = "type", required = false)  List<Long> type){
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }

        HashMap<Object, Object> map = vacancyService.findMinMax(min, max);
        List<Vacancy> vacancies = vacancyService.sortAndFilter((Integer) map.get("min"), (Integer) map.get("max"), sort, category, type);

        model.addAttribute("min", map.get("minSalary"));
        model.addAttribute("max", map.get("maxSalary"));
        model.addAttribute("categories", categoryService.updateTotal(vacancies));
        model.addAttribute("total", vacancies.size());
        model.addAttribute("type", typeService.updateTotal(vacancies));
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        return "search-list";
    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String addVacancy(Principal principal, Model model){
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "vacancy";
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping("/new")
    public String addVacancy(Principal principal,
                             @RequestParam("category") String cat,
                             @RequestParam("type") String t,
                             @RequestParam("cityString") String cityName,
                             @RequestParam("description") String desc,
                             @RequestParam("salary") int salary){
        Vacancy vacancy = new Vacancy();
        vacancy.setUser(userService.findUser(principal.getName()));
        vacancy.setCity(cityService.findByName(cityName));
        vacancy.setType(typeService.findByName(t));
        vacancy.setCategory(categoryService.findByName(cat));
        vacancy.setDescription(desc);
        vacancy.setSalary(salary);
        vacancyService.save(vacancy);
        return "redirect:/";
    }

}