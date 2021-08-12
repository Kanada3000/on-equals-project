package org.onequals.controller;

import org.onequals.domain.User;
import org.onequals.domain.Vacancy;
import org.onequals.recaptcha.ReCaptchaResponse;
import org.onequals.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    private final CategoryService categoryService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;
    private final ReCaptchaRegisterService reCaptchaRegisterService;

    public VacancyController(CategoryService categoryService, VacancyService vacancyService,
                             UserService userService, TypeService typeService,
                             CityService cityService, ReCaptchaRegisterService reCaptchaRegisterService) {
        this.categoryService = categoryService;
        this.vacancyService = vacancyService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.reCaptchaRegisterService = reCaptchaRegisterService;
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
                              @RequestParam(required = false) String dislikes,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        model.addAttribute("minVal", min);
        model.addAttribute("maxVal", max);
        model.addAttribute("currentPage", currentPage);

        User user = new User();

        if (principal != null) {
            user = userService.findUser(principal.getName());
        }

        if (likes != null)
            if (!likes.isEmpty()) {
                List<String> items = Arrays.asList(likes.split("&"));
                userService.addVacancyLikes(user, items);
            }

        if (dislikes != null)
            if (!dislikes.isEmpty()) {
                List<String> items = Arrays.asList(dislikes.split("&"));
                userService.deleteVacancyLikes(user, items);
            }

        if (user.getLikedVacancy() != null)
            if (!user.getLikedVacancy().isEmpty()) {
                StringBuilder s = new StringBuilder();
                for (Vacancy v : user.getLikedVacancy()) {
                    s.append(v.getId()).append("&");
                }
                model.addAttribute("likesId", s);
            }

        HashMap<Object, Object> map = vacancyService.findMinMax(min, max);
        List<Vacancy> vacancies = vacancyService.sortAndFilter(key_words, catString, citString,
                (Integer) map.get("min"), (Integer) map.get("max"), sort, category, type);

        model.addAttribute("min", map.get("minSalary"));
        model.addAttribute("max", map.get("maxSalary"));
        if (vacancies != null) {
            if (!vacancies.isEmpty()) {
                model.addAttribute("categories", categoryService.updateTotal(vacancies));
                model.addAttribute("type", typeService.updateTotal(vacancies));
                model.addAttribute("total", vacancies.size());
                Page<Vacancy> vacancyPage = vacancyService.findPaginated(PageRequest.of(currentPage - 1, pageSize), vacancies);
                model.addAttribute("vacancies", vacancyPage);
                int totalPages = vacancyPage.getTotalPages();
                if (totalPages > 0) {
                    List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                            .boxed()
                            .collect(Collectors.toList());
                    model.addAttribute("pageNumbers", pageNumbers);
                    model.addAttribute("totalPages", totalPages);
                }
            } else {
                model.addAttribute("categories", categoryService.getAll());
                model.addAttribute("type", typeService.getAll());
                model.addAttribute("total", "0");
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
        model.addAttribute("sort", sort);
        model.addAttribute("category", category);
        model.addAttribute("typeVal", type);
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("userType", "seeker");

        return "search-list";
    }

    @GetMapping("/new")
    public String addVacancy(Principal principal, Model model) {
        if (principal != null) {
            User user = userService.findUser(principal.getName());
            model.addAttribute("nameProfile", user.getName());
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("country", cityService.getAllCountry());
        return "vacancy";
    }

    @PostMapping("/new")
    public String addVacancy(Principal principal,
                             Model model,
                             @RequestParam(name = "categoryList") List<String> catList,
                             @RequestParam("typeList") List<String> typeList,
                             @RequestParam("citString") List<String> citString,
                             @RequestParam("description") List<String> desc,
                             @RequestParam("salary") List<Integer> salary,
                             @RequestParam("g-recaptcha-response") String response) {
        ReCaptchaResponse reCaptchaResponse = reCaptchaRegisterService.verify(response);

        if (!reCaptchaResponse.isSuccess()) {
            return "error";
        }
        catList = catList.stream().map(c -> c.replaceAll("%", ",")).collect(Collectors.toList());
        User user = userService.findUser(principal.getName());
        for (int i = 0; i < typeList.size(); i++) {
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
            Vacancy vacancy = new Vacancy();
            vacancy.setUser(user);
            vacancy.setType(typeService.findByName(typeList.get(i)));
            vacancy.setCategory(categoryService.findByName(catList.get(i)));
            vacancy.setDescription(desc.get(i));
            vacancy.setSalary(salary.get(i));
            cityService.addCities(vacancy, cityService.findByNames(cities));
        }
        return "redirect:/index/vacancy";
    }

}