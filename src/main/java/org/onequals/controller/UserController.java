package org.onequals.controller;

import org.onequals.domain.*;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/profile")
@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final UserService userService;
    private final EmployerService employerService;
    private final SeekerService seekerService;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;
    private final StorageService storageService;
    private final CityService cityService;
    private final CategoryService categoryService;
    private final TypeService typeService;

    public UserController(UserService userService, EmployerService employerService,
                          SeekerService seekerService, VacancyService vacancyService,
                          ResumeService resumeService, StorageService storageService,
                          CityService cityService, CategoryService categoryService,
                          TypeService typeService) {
        this.userService = userService;
        this.employerService = employerService;
        this.seekerService = seekerService;
        this.vacancyService = vacancyService;
        this.resumeService = resumeService;
        this.storageService = storageService;
        this.cityService = cityService;
        this.categoryService = categoryService;
        this.typeService = typeService;
    }


    @GetMapping
    public String profilePage(Principal principal, Model model) {
        User user = userService.findUser(principal.getName());
        if (!user.getRoles().contains(Role.EMPLOYER) && !user.getRoles().contains(Role.SEEKER)) {
            return "redirect:/choose";
        }
        if (user.getRoles().contains(Role.EMPLOYER)) {
            model.addAttribute("user", employerService.findByUser(user));
            model.addAttribute("likes", user.getLikedResume());
            model.addAttribute("block", vacancyService.findByUser(user));
        } else if (user.getRoles().contains(Role.SEEKER)) {
            model.addAttribute("user", seekerService.findByUser(user));
            model.addAttribute("likes", user.getLikedVacancy());
            model.addAttribute("block", resumeService.findByUser(user));
            model.addAttribute("map", storageService.findFiles(user, true));
        }
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("types", typeService.getAll());
        if (user.getPhoto() != null)
            if (!user.getPhoto().isEmpty()) {
                model.addAttribute("photo", user.getPhoto());
            }
        return "profile";
    }

    @GetMapping("/delete")
    public String profileDelete(Principal principal) {
        User user = userService.findUser(principal.getName());
        userService.delete(user.getId());
        userService.clearSession();
        return "redirect:/";
    }

    @GetMapping("/delete/vacancy/{id}")
    public String deleteVacancy(@PathVariable Long id) {
        vacancyService.delete(id);
        return "redirect:/profile";
    }

    @GetMapping("/delete/resume/{id}")
    public String deleteResume(@PathVariable Long id) {
        resumeService.delete(id);
        return "redirect:/profile";
    }

    @PostMapping("/deleteLike")
    public String deleteLike(Principal principal,
                             @RequestParam("likeId") List<String> id,
                             @RequestParam(required = false) Long userId) {
        User user = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            userService.deleteResumeLikes(user, id);
        } else if (user.getRoles().contains(Role.SEEKER)) {
            userService.deleteVacancyLikes(user, id);
        }
        if (userId != null) {
            return "redirect:/profile/user/" + userId;
        } else
            return "redirect:/profile";
    }

    @PostMapping("/addLike")
    public String addLike(Principal principal,
                          @RequestParam("likeId") List<String> id,
                          @RequestParam(required = false) Long userId) {
        User user = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            userService.addResumeLikes(user, id);
        } else if (user.getRoles().contains(Role.SEEKER)) {
            userService.addVacancyLikes(user, id);
        }
        if (userId != null) {
            return "redirect:/profile/user/" + userId;
        } else
            return "redirect:/profile";
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestParam String path) {
        storageService.deleteFile(path);
        return "redirect:/profile";
    }

    @GetMapping("/hide")
    public String hideUser(Principal principal) {
        User user = userService.findUser(principal.getName());
        user.setHidden(!user.getHidden());
        userService.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/user/{id}")
    public String profileUser(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findById(id).get();
        User currentUser = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            model.addAttribute("user", employerService.findByUser(user));
            model.addAttribute("block", vacancyService.findByUser(user));
        } else if (user.getRoles().contains(Role.SEEKER)) {
            model.addAttribute("user", seekerService.findByUser(user));
            model.addAttribute("block", resumeService.findByUser(user));
            model.addAttribute("map", storageService.findFiles(user, true));
        }
        if (currentUser.getRoles().contains(Role.EMPLOYER)) {
            if (currentUser.getLikedResume() != null)
                if (!currentUser.getLikedResume().isEmpty()) {
                    StringBuilder s = new StringBuilder();
                    for (Resume r : currentUser.getLikedResume()) {
                        s.append(r.getId()).append("&");
                    }
                    model.addAttribute("likesId", s);
                }
        } else if (currentUser.getRoles().contains(Role.SEEKER)) {
            if (currentUser.getLikedVacancy() != null)
                if (!currentUser.getLikedVacancy().isEmpty()) {
                    StringBuilder s = new StringBuilder();
                    for (Vacancy v : currentUser.getLikedVacancy()) {
                        s.append(v.getId()).append("&");
                    }
                    model.addAttribute("likesId", s);
                }
        }
        return "profile-guest";
    }

    @PostMapping("/editProfile")
    public String editProfile(Principal principal,
                              @RequestParam String name,
                              @RequestParam(required = false) String site,
                              @RequestParam(required = false) String cities,
                              @RequestParam(required = false) String fb,
                              @RequestParam(required = false) String inst,
                              @RequestParam(required = false) String tw,
                              @RequestParam(required = false) String li,
                              @RequestParam(required = false) String description) {
        User userDB = userService.findUser(principal.getName());
        if (userDB.getRoles().contains(Role.EMPLOYER)) {
            Employer user = employerService.findByUser(userDB);
            int i = 0;
            if (!user.getName().equals(name)) {
                i = 1;
                user.setName(name);
                userDB.setName(name);
                userService.save(userDB);
            }
            if (!user.getSite().equals(site)) {
                i = 1;
                user.setSite(site);
            }
            if (fb != null)
                if (user.getLinkFacebook() != fb) {
                    i = 1;
                    user.setLinkFacebook(fb);
                }
            if (inst != null)
                if (user.getLinkInstagram() != inst) {
                    i = 1;
                    user.setLinkInstagram(inst);
                }
            if (tw != null)
                if (user.getLinkTwitter() != tw) {
                    i = 1;
                    user.setLinkTwitter(tw);
                }
            if (li != null)
                if (user.getLinkLinkedIn() != li) {
                    i = 1;
                    user.setLinkLinkedIn(li);
                }
            if (description != null)
                if (user.getDescription() != description) {
                    i = 1;
                    user.setDescription(description);
                }
            if (!cities.equals("")) {
                i = 1;
                List<String> city = Arrays.asList(cities.split("\\$"));
                user.setCity(null);
                cityService.addCities(user, cityService.findByNames(city));
            }
            if (i == 1) {
                employerService.save(user);
                userService.auth(userDB);
            }
        } else if (userDB.getRoles().contains(Role.SEEKER)) {
            Seeker user = seekerService.findByUser(userDB);
            int i = 0;
            if (!user.getName().equals(name)) {
                i = 1;
                user.setName(name);
                userDB.setName(name);
                userService.save(userDB);
            }
            if (!user.getSite().equals(site)) {
                i = 1;
                user.setSite(site);
            }
            if (fb != null)
                if (user.getLinkFacebook() != fb) {
                    i = 1;
                    user.setLinkFacebook(fb);
                }
            if (inst != null)
                if (user.getLinkInstagram() != inst) {
                    i = 1;
                    user.setLinkInstagram(inst);
                }
            if (tw != null)
                if (user.getLinkTwitter() != tw) {
                    i = 1;
                    user.setLinkTwitter(tw);
                }
            if (li != null)
                if (user.getLinkLinkedIn() != li) {
                    i = 1;
                    user.setLinkLinkedIn(li);
                }
            if (description != null)
                if (user.getDescription() != description) {
                    i = 1;
                    user.setDescription(description);
                }
            if (!cities.equals("")) {
                i = 1;
                List<String> city = Arrays.asList(cities.split("\\$"));
                user.setCity(null);
                cityService.addCities(user, cityService.findByNames(city));
            }
            if (i == 1) {
                seekerService.save(user);
                userService.auth(userDB);
            }
        }

        return "redirect:/profile";
    }

    @PostMapping("/editVacancy")
    public String editVacancy(Principal principal,
                              @RequestParam Long id,
                              @RequestParam String category,
                              @RequestParam String type,
                              @RequestParam(required = false) String city,
                              @RequestParam String descript,
                              @RequestParam int salary) {
        User user = userService.findUser(principal.getName());
        if (user.getRoles().contains(Role.EMPLOYER)) {
            Vacancy vacancy = vacancyService.getById(id);
            Category categoryDB = categoryService.findByName(category);
            Type typeDB = typeService.findByName(type);
            if (vacancy.getCategory() != categoryDB)
                vacancy.setCategory(categoryDB);
            if (vacancy.getType() != typeDB)
                vacancy.setType(typeDB);
            if (!city.equals("")) {
                List<String> cities = Arrays.asList(city.split("\\$"));
                vacancy.setCity(null);
                cityService.addCities(vacancy, cityService.findByNames(cities));
            }
            if (!vacancy.getDescription().equals(descript))
                vacancy.setDescription(descript);
            if (vacancy.getSalary() != salary)
                vacancy.setSalary(salary);
            vacancyService.save(vacancy);
        } else if (user.getRoles().contains(Role.SEEKER)) {
            Resume resume = resumeService.getById(id);
            Category categoryDB = categoryService.findByName(category);
            Type typeDB = typeService.findByName(type);
            if (resume.getCategory() != categoryDB)
                resume.setCategory(categoryDB);
            if (resume.getType() != typeDB)
                resume.setType(typeDB);
            if (!city.equals("")) {
                List<String> cities = Arrays.asList(city.split("\\$"));
                resume.setCity(null);
                cityService.addCities(resume, cityService.findByNames(cities));
            }
            if (!resume.getDescription().equals(descript))
                resume.setDescription(descript);
            if (resume.getSalary() != salary)
                resume.setSalary(salary);
            resumeService.save(resume);
        }
        return "redirect:/profile";
    }

    @PostMapping("/add-photo")
    public String addPhoto(Principal principal, @RequestParam MultipartFile file) throws IOException {
        User user = userService.findUser(principal.getName());
        user.setPhoto(storageService.uploadPhoto(file, principal.getName()));
        userService.save(user);
        return "redirect:/profile";
    }
}