package org.onequals.controller;

import org.onequals.domain.*;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;
    private final ResumeService resumeService;

    public AdminController(CategoryService categoryService, VacancyService vacancyService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService) {
        this.categoryService = categoryService;
        this.vacancyService = vacancyService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.resumeService = resumeService;
    }

    @GetMapping("/category")
    public String adminCategoryPage(Principal principal, Model model) {
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("category", categoryService.getAll());
        return "admin/category";
    }

    @PostMapping("/category/add")
    public String adminCategoryAdd(@ModelAttribute() Category category) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String adminCategoryDelete(@PathVariable("id") long id) {
        categoryService.delete(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/cities")
    public String adminCityPage(Principal principal, Model model) {
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("city", cityService.getAll());
        return "admin/cities";
    }

    @PostMapping("/cities/add")
    public String adminCityAdd(@RequestParam(required = false) Long id,
                               @RequestParam String city,
                               @RequestParam String country) {
        City cityObj = new City();
        if (id != null)
            cityObj.setId(id);
        cityObj.setCity(city);
        cityObj.setCountry(country);
        cityService.save(cityObj);
        return "redirect:/admin/cities";
    }

    @GetMapping("/cities/delete/{id}")
    public String adminCityDelete(@PathVariable("id") long id) {
        cityService.delete(id);
        return "redirect:/admin/cities";
    }

    @GetMapping("/users")
    public String adminUsersPage(Principal principal, Model model) {
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("user", userService.getAll());
        return "admin/users";
    }

    @PostMapping("/users/add")
    public String adminUserAdd(@ModelAttribute() User user) {
        user.setPassword(userService.setPassword(user.getPassword()));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String adminUserDelete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/vacancies")
    public String adminVacanciesPage(Principal principal, Model model) {
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("vacancy", vacancyService.getAll());
        model.addAttribute("user", userService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        return "admin/vacancies";
    }

    @PostMapping("/vacancies/add")
    public String adminVacanciesAdd(@RequestParam(name="id", required = false) Long id,
                                    @RequestParam(name="user", required = false) String user,
                                    @RequestParam(name="type", required = false) String type,
                                    @RequestParam(name="category", required = false) String category,
                                    @RequestParam(name="city", required = false) String city,
                                    @RequestParam(name="salary", required = false) int salary,
                                    @RequestParam(name="description", required = false) String description) {
        Vacancy vacancy = new Vacancy();
        if(id != null)
            vacancy.setId(id);
        if (user != null)
            vacancy.setUser(userService.findUser(user));
        if(type != null)
            vacancy.setType(typeService.findByName(type));
        if(category != null)
            vacancy.setCategory(categoryService.findByName(category));
        if(city != null)
            vacancy.setCity(cityService.findByName(city));
        System.out.println(salary);
        if(salary != 0)
            vacancy.setSalary(salary);
        if(description != null)
            vacancy.setDescription(description);
        vacancyService.save(vacancy);

        return "redirect:/admin/vacancies";
    }

    @GetMapping("/vacancies/delete/{id}")
    public String adminVacancyDelete(@PathVariable("id") long id) {
        vacancyService.delete(id);
        return "redirect:/admin/vacancies";
    }

    @GetMapping("/resumes")
    public String adminResumePage(Principal principal, Model model) {
        if(principal != null){
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("resume", resumeService.getAll());
        model.addAttribute("user", userService.getAll());
        model.addAttribute("types", typeService.getAll());
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        return "admin/resumes";
    }

    @PostMapping("/resumes/add")
    public String adminResumesAdd(@RequestParam(name="id", required = false) Long id,
                                    @RequestParam(name="user", required = false) String user,
                                    @RequestParam(name="type", required = false) String type,
                                    @RequestParam(name="category", required = false) String category,
                                    @RequestParam(name="city", required = false) String city,
                                    @RequestParam(name="salary", required = false) int salary,
                                    @RequestParam(name="description", required = false) String description) {
        Resume resume = new Resume();
        if(id != null)
            resume.setId(id);
        if (user != null)
            resume.setUser(userService.findUser(user));
        if(type != null)
            resume.setType(typeService.findByName(type));
        if(category != null)
            resume.setCategory(categoryService.findByName(category));
        if(city != null)
            resume.setCity(cityService.findByName(city));
        if(salary != 0)
            resume.setSalary(salary);
        if(description != null)
            resume.setDescription(description);
        resumeService.save(resume);

        return "redirect:/admin/resumes";
    }

    @GetMapping("/resumes/delete/{id}")
    public String adminResumeDelete(@PathVariable("id") long id) {
        resumeService.delete(id);
        return "redirect:/admin/resumes";
    }

}