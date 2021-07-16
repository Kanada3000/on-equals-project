package org.onequals.controller;

import org.onequals.domain.Resume;
import org.onequals.domain.User;
import org.onequals.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final StorageService storageService;

    public ResumeController(CategoryService categoryService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService, StorageService storageService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.resumeService = resumeService;
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public String vacancyList(Principal principal, Model model,
                              @RequestParam(name = "sort", defaultValue = "id") String sort,
                              @RequestParam(name = "min", required = false) Integer min,
                              @RequestParam(name = "max", required = false) Integer max,
                              @RequestParam(name = "category", required = false) List<Long> category,
                              @RequestParam(name = "type", required = false) List<Long> type,
                              @RequestParam(required = false) String key_words,
                              @RequestParam(required = false) String catString,
                              @RequestParam(required = false) String citString,
                              @RequestParam(required = false) String likes,
                              @RequestParam(required = false) String dislikes) {
        User user = userService.findUser(principal.getName());

        if (likes != null) {
            List<String> items = Arrays.asList(likes.split("&"));
            userService.addResumeLikes(user, items);
        }

        if (dislikes != null) {
            List<String> items = Arrays.asList(dislikes.split("&"));
            userService.deleteResumeLikes(user, items);
        }

        if (user.getLikedResume() != null)
            if (!user.getLikedResume().isEmpty()) {
                StringBuilder s = new StringBuilder();
                for (Resume r : user.getLikedResume()) {
                    s.append(r.getId()).append("&");
                }
                model.addAttribute("likesId", s);
            }

        HashMap<Object, Object> map = resumeService.findMinMax(min, max);
        List<Resume> resumes = resumeService.sortAndFilter(key_words, catString, citString,
                (Integer) map.get("min"), (Integer) map.get("max"), sort, category, type);

        model.addAttribute("min", map.get("minSalary"));
        model.addAttribute("max", map.get("maxSalary"));

        if (resumes != null) {
            if (!resumes.isEmpty()) {
                model.addAttribute("categories", categoryService.updateTotalResumes(resumes));
                model.addAttribute("type", typeService.updateTotalResumes(resumes));
                model.addAttribute("total", resumes.size());
                model.addAttribute("vacancies", resumes);
            }
        } else {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("type", typeService.getAll());
            model.addAttribute("total", "0");
            model.addAttribute("vacancies", null);
        }

        model.addAttribute("key_wordsVal", key_words);
        model.addAttribute("catStringVal", catString);
        model.addAttribute("citStringVal", citString);
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("userType", "employer");
        return "search-list";
    }

    @GetMapping("/new")
    public String addResume(Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "resume-finder";
    }

    @PostMapping("/new")
    public String addResume(Principal principal,
                            @RequestParam("category") List<String> cat,
                            @RequestParam("typeList") List<String> typeList,
                            @RequestParam("citString") List<String> citString,
                            @RequestParam("description") List<String> desc,
                            @RequestParam("salary") List<Integer> salary) {
        User user = userService.findUser(principal.getName());

        for (int i = 0; i < cat.size(); i++) {
            List<String> cities = new ArrayList<>();
            int j = 0;
            for (String c : citString) {
                if (j != 0 && c.equals(".")) {
                    break;
                } else if (!c.equals(".")) {
                    cities.add(c);
                }
                j++;
            }
            citString.subList(0, j).clear();
            Resume resume = new Resume();

            resume.setUser(user);
            resume.setType(typeService.findByName(typeList.get(i)));
            resume.setCategory(categoryService.findByName(cat.get(i)));
            resume.setDescription(desc.get(i));
            resume.setSalary(salary.get(i));
            cityService.addCities(resume, cityService.findByNames(cities));
        }
        return "redirect:/";
    }

    @PostMapping("/file")
    public String saveFile(Principal principal, @RequestParam MultipartFile file){
        storageService.uploadFile(file, principal.getName());
        return "redirect:/";
    }

}