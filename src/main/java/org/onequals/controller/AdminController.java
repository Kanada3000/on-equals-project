package org.onequals.controller;

import org.onequals.domain.*;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    private final EmployerService employerService;
    private final SeekerService seekerService;

    public AdminController(CategoryService categoryService, VacancyService vacancyService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService, EmployerService employerService, SeekerService seekerService) {
        this.categoryService = categoryService;
        this.vacancyService = vacancyService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.resumeService = resumeService;
        this.employerService = employerService;
        this.seekerService = seekerService;
    }

    @GetMapping("/category")
    public String adminCategoryPage(Model model) {
        model.addAttribute("category", categoryService.getAllAll());
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

    @GetMapping("/type")
    public String adminTypePage(Model model) {
        model.addAttribute("types", typeService.getAllAll());
        return "admin/type";
    }

    @PostMapping("/type/add")
    public String adminTypeAdd(@ModelAttribute() Type type) {
        typeService.save(type);
        return "redirect:/admin/type";
    }

    @GetMapping("/type/delete/{id}")
    public String adminTypeDelete(@PathVariable("id") long id) {
        typeService.delete(id);
        return "redirect:/admin/type";
    }

    @GetMapping("/cities")
    public String adminCityPage(Model model) {
        model.addAttribute("city", cityService.getAllAll());
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
    public String adminUsersPage(Model model) {
        model.addAttribute("user", userService.getAll());
        return "admin/users";
    }

    @PostMapping("/users/add")
    public String adminUserAdd(@ModelAttribute() User user, @RequestParam("role") String role) {
        User userDB = userService.findById(user.getId()).get();
        if (!userDB.getPassword().equals(user.getPassword()))
            user.setPassword(userService.setPassword(user.getPassword()));
        Set<Role> set = new TreeSet<Role>();
        set.add(Role.USER);
        switch (role) {
            case "SEEKER" -> {
                set.add(Role.SEEKER);
            }
            case "EMPLOYER" -> {
                set.add(Role.EMPLOYER);
            }
            case "ADMIN" -> {
                set.add(Role.ADMIN);
                set.remove(Role.USER);
            }
        }
        user.setRoles(set);
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
        model.addAttribute("vacancy", vacancyService.getAll());
        model.addAttribute("user", userService.getAllUser("employer"));
        model.addAttribute("types", typeService.getAllAll());
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("city", cityService.getAllAll());
        return "admin/vacancies";
    }

    @PostMapping("/vacancies/add")
    public String adminVacanciesAdd(@RequestParam(name = "id", required = false) Long id,
                                    @RequestParam(name = "user", required = false) String user,
                                    @RequestParam(name = "type", required = false) String type,
                                    @RequestParam(name = "category", required = false) String category,
                                    @RequestParam(name = "city", required = false) List<String> city,
                                    @RequestParam(name = "salary", required = false) int salary,
                                    @RequestParam(name = "description", required = false) String description) {
        Vacancy vacancy = new Vacancy();
        if (id != null)
            vacancy.setId(id);
        if (user != null)
            vacancy.setUser(userService.findUser(user));
        if (type != null)
            vacancy.setType(typeService.findByName(type));
        if (category != null)
            vacancy.setCategory(categoryService.findByName(category));
        if (city != null)
            cityService.addCities(vacancy, cityService.findByNames(city));
        if (salary != 0)
            vacancy.setSalary(salary);
        if (description != null)
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
    public String adminResumePage(Model model) {
        model.addAttribute("resume", resumeService.getAll());
        model.addAttribute("user", userService.getAllUser("seeker"));
        model.addAttribute("types", typeService.getAllAll());
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("city", cityService.getAllAll());
        return "admin/resumes";
    }

    @PostMapping("/resumes/add")
    public String adminResumesAdd(@RequestParam(name = "id", required = false) Long id,
                                  @RequestParam(name = "user", required = false) String user,
                                  @RequestParam(name = "type", required = false) String type,
                                  @RequestParam(name = "category", required = false) String category,
                                  @RequestParam(name = "city", required = false) List<String> city,
                                  @RequestParam(name = "salary", required = false) int salary,
                                  @RequestParam(name = "description", required = false) String description) {
        Resume resume = new Resume();
        if (id != null)
            resume.setId(id);
        if (user != null)
            resume.setUser(userService.findUser(user));
        if (type != null)
            resume.setType(typeService.findByName(type));
        if (category != null)
            resume.setCategory(categoryService.findByName(category));
        if (city != null)
            cityService.addCities(resume, cityService.findByNames(city));
        if (salary != 0)
            resume.setSalary(salary);
        if (description != null)
            resume.setDescription(description);
        resumeService.save(resume);

        return "redirect:/admin/resumes";
    }

    @GetMapping("/resumes/delete/{id}")
    public String adminResumeDelete(@PathVariable("id") long id) {
        resumeService.delete(id);
        return "redirect:/admin/resumes";
    }

    @GetMapping("/employer")
    public String adminEmployerPage(Model model) {
        model.addAttribute("employer", employerService.getAll());
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("user", userService.getAllUser("user"));
        model.addAttribute("city", cityService.getAllAll());

        return "admin/employer";
    }

    @PostMapping("/employer/add")
    public String adminEmployerAdd(@RequestParam(required = false) Long id,
                                   @RequestParam String user,
                                   @RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String category,
                                   @RequestParam List<String> city,
                                   @RequestParam(required = false) String site,
                                   @RequestParam String description,
                                   @RequestParam int age,
                                   @RequestParam int emp_count,
                                   @RequestParam int size,
                                   @RequestParam(required = false) String link_facebook,
                                   @RequestParam(required = false) String link_instagram,
                                   @RequestParam(required = false) String link_linked_in,
                                   @RequestParam(required = false) String link_twitter) {
        Employer emp = new Employer();
        if (id != null)
            emp.setId(id);
        if (site != null)
            emp.setSite(site);
        if (link_facebook != null)
            emp.setLinkFacebook(link_facebook);
        if (link_instagram != null)
            emp.setLinkInstagram(link_instagram);
        if (link_linked_in != null)
            emp.setLinkLinkedIn(link_linked_in);
        if (link_twitter != null)
            emp.setLinkTwitter(link_twitter);

        User userDB = userService.findUser(user);

        emp.setUser(userDB);
        emp.setName(name);
        emp.setEmail(email);
        emp.setCategory(categoryService.findByName(category));
        cityService.addCities(emp, cityService.findByNames(city));
        emp.setDescription(description);
        emp.setAge(age);
        emp.setEmpCount(emp_count);
        emp.setSize(size);


        userService.addRole(userDB, Role.EMPLOYER);
        employerService.save(emp);
        return "redirect:/admin/employer";
    }

    @GetMapping("/employer/delete/{id}")
    public String adminEmployerDelete(@PathVariable("id") long id) {
        Employer employer = employerService.findById(id);
        User user = employer.getUser();
        userService.removeRole(user, Role.EMPLOYER);
        employerService.delete(id);
        return "redirect:/admin/employer";
    }

    @GetMapping("/seeker")
    public String adminSeekerPage(Model model) {
        model.addAttribute("seeker", seekerService.getAll());
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("user", userService.getAllUser("user"));
        model.addAttribute("city", cityService.getAllAll());

        return "admin/seeker";
    }

    @PostMapping("/seeker/add")
    public String adminSeekerAdd(@RequestParam(required = false) Long id,
                                   @RequestParam String user,
                                   @RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String category,
                                   @RequestParam List<String> city,
                                   @RequestParam(required = false) String site,
                                   @RequestParam String description,
                                   @RequestParam(required = false) String link_facebook,
                                   @RequestParam(required = false) String link_instagram,
                                   @RequestParam(required = false) String link_linked_in,
                                   @RequestParam(required = false) String link_twitter) {
        Seeker seek = new Seeker();
        if (id != null)
            seek.setId(id);
        if (site != null)
            seek.setSite(site);
        if (link_facebook != null)
            seek.setLinkFacebook(link_facebook);
        if (link_instagram != null)
            seek.setLinkInstagram(link_instagram);
        if (link_linked_in != null)
            seek.setLinkLinkedIn(link_linked_in);
        if (link_twitter != null)
            seek.setLinkTwitter(link_twitter);

        User userDB = userService.findUser(user);

        seek.setUser(userDB);
        seek.setName(name);
        seek.setEmail(email);
        seek.setCategory(categoryService.findByName(category));
        cityService.addCities(seek, cityService.findByNames(city));
        seek.setDescription(description);


        userService.addRole(userDB, Role.SEEKER);
        seekerService.save(seek);
        return "redirect:/admin/seeker";
    }

    @GetMapping("/seeker/delete/{id}")
    public String adminSeekerDelete(@PathVariable("id") long id) {
        Seeker seeker = seekerService.findById(id);
        User user = seeker.getUser();
        userService.removeRole(user, Role.SEEKER);
        seekerService.delete(id);
        return "redirect:/admin/seeker";
    }

}