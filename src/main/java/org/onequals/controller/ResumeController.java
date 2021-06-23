package org.onequals.controller;

import org.onequals.domain.Resume;
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
@RequestMapping("/resume")
public class ResumeController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;
    private final ResumeService resumeService;

    public ResumeController(CategoryService categoryService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.resumeService = resumeService;
    }

//    @GetMapping("/list")
//    public String vacancyList(Principal principal, Model model,
//                              @RequestParam(name = "sort", defaultValue = "id")  String sort,
//                              @RequestParam(name = "min", required = false)  Integer min,
//                              @RequestParam(name = "max", required = false)  Integer max,
//                              @RequestParam(name = "category", required = false)  List<Long> category,
//                              @RequestParam(name = "type", required = false)  List<Long> type){
//        if(principal != null){
//            User user = userService.findUser(principal.getName());
//            model.addAttribute("nameProfile", user.getName());
//        }
//
//        HashMap<Object, Object> map = vacancyService.findMinMax(min, max);
//        List<Vacancy> vacancies = vacancyService.sortAndFilter((Integer) map.get("min"), (Integer) map.get("max"), sort, category, type);
//
//        model.addAttribute("min", map.get("minSalary"));
//        model.addAttribute("max", map.get("maxSalary"));
//        model.addAttribute("categories", categoryService.updateTotal(vacancies));
//        model.addAttribute("total", vacancies.size());
//        model.addAttribute("type", typeService.updateTotal(vacancies));
//        model.addAttribute("vacancies", vacancies);
//        model.addAttribute("category", categoryService.getAll());
//        model.addAttribute("city", cityService.getAll());
//        return "search-list";
//    }


    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String addResume(Principal principal, Model model){
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "resume-finder";
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping("/new")
    public String addResume(Principal principal,
                             @RequestParam("category") String cat,
                             @RequestParam("type") String t,
                             @RequestParam("citString") String cityName,
                             @RequestParam("description") String desc,
                             @RequestParam("salary") int salary){
        Resume resume = new Resume();
        resume.setUser(userService.findUser(principal.getName()));
        resume.setCity(cityService.findByName(cityName));
        resume.setType(typeService.findByName(t));
        resume.setCategory(categoryService.findByName(cat));
        resume.setDescription(desc);
        resume.setSalary(salary);
        resumeService.save(resume);
        return "redirect:/";
    }

}