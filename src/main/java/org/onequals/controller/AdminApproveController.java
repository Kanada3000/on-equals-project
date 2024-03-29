package org.onequals.controller;

import org.onequals.domain.*;
import org.onequals.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin/approve")
public class AdminApproveController {
    private final CategoryService categoryService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;
    private final ResumeService resumeService;
    private final EmployerService employerService;
    private final SeekerService seekerService;
    private final StorageService storageService;

    public AdminApproveController(CategoryService categoryService, VacancyService vacancyService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService, EmployerService employerService, SeekerService seekerService, StorageService storageService) {
        this.categoryService = categoryService;
        this.vacancyService = vacancyService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.resumeService = resumeService;
        this.employerService = employerService;
        this.seekerService = seekerService;
        this.storageService = storageService;
    }

    @GetMapping("/vacancy")
    public String adminVacanciesPage(Model model,
                                     @RequestParam("sort") Optional<String> sortVal,
                                     @RequestParam(required = false) String fieldName,
                                     @RequestParam(required = false) String searchField,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        String sort = sortVal.orElse("id");
        List<Vacancy> unapproved = vacancyService.getUnapproved(sort, fieldName, searchField);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        Page<Vacancy> pageObj = vacancyService.findPaginated(PageRequest.of(currentPage - 1, pageSize), unapproved);
        int totalPages = pageObj.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("vacancy", pageObj);
        model.addAttribute("user", userService.getAllUser("employer"));
        model.addAttribute("types", typeService.getAllAll());
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("city", cityService.getAllAll());
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", unapproved);
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("sort", sort);
        model.addAttribute("fieldName", fieldName);
        model.addAttribute("searchField", searchField);
        model.addAttribute("fields", Arrays.asList("id", "user", "type", "category", "city", "salary", "description"));
        return "admin/approve/vacancy";
    }

    @PostMapping("/vacancy/add")
    public String adminVacanciesAdd(@RequestParam(name = "id", required = false) Long id,
                                    @RequestParam(name = "user", required = false) String user,
                                    @RequestParam(name = "type", required = false) String type,
                                    @RequestParam(name = "category", required = false) String category,
                                    @RequestParam(name = "city", required = false) List<String> city,
                                    @RequestParam(name = "salary", required = false) int salary,
                                    @RequestParam(name = "description", required = false) String description,
                                    @RequestParam(name = "approved", required = false) Boolean approved) {
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
        if (approved != null)
            vacancy.setApproved(approved);
        vacancyService.save(vacancy);

        return "redirect:/admin/approve/vacancy";
    }

    @GetMapping("/vacancy/delete/{id}")
    public String adminVacancyDelete(@PathVariable("id") long id) {
        vacancyService.delete(id);
        return "redirect:/admin/approve/vacancy";
    }

    @GetMapping("/resume")
    public String adminResumePage(Model model,
                                  @RequestParam("sort") Optional<String> sortVal,
                                  @RequestParam(required = false) String fieldName,
                                  @RequestParam(required = false) String searchField,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        String sort = sortVal.orElse("id");
        List<Resume> unapproved = resumeService.getUnapproved(sort, fieldName, searchField);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        Page<Resume> pageObj = resumeService.findPaginated(PageRequest.of(currentPage - 1, pageSize), unapproved);
        int totalPages = pageObj.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("resume", pageObj);
        model.addAttribute("user", userService.getAllUser("seeker"));
        model.addAttribute("types", typeService.getAllAll());
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("city", cityService.getAllAll());
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", unapproved);
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("sort", sort);
        model.addAttribute("fieldName", fieldName);
        model.addAttribute("searchField", searchField);
        model.addAttribute("fields", Arrays.asList("id", "user", "type", "category", "city", "salary", "description"));
        return "admin/approve/resume";
    }

    @PostMapping("/resume/add")
    public String adminResumesAdd(@RequestParam(name = "id", required = false) Long id,
                                  @RequestParam(name = "user", required = false) String user,
                                  @RequestParam(name = "type", required = false) String type,
                                  @RequestParam(name = "category", required = false) String category,
                                  @RequestParam(name = "city", required = false) List<String> city,
                                  @RequestParam(name = "salary", required = false) int salary,
                                  @RequestParam(name = "description", required = false) String description,
                                  @RequestParam(name = "approved", required = false) Boolean approved) {
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
        if (approved != null)
            resume.setApproved(approved);
        resumeService.save(resume);

        return "redirect:/admin/approve/resume";
    }

    @GetMapping("/resumes/delete/{id}")
    public String adminResumeDelete(@PathVariable("id") long id) {
        resumeService.delete(id);
        return "redirect:/admin/approve/resumes";
    }

    @GetMapping("/employer")
    public String adminEmployerPage(Model model,
                                    @RequestParam("sort") Optional<String> sortVal,
                                    @RequestParam(required = false) String fieldName,
                                    @RequestParam(required = false) String searchField,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        String sort = sortVal.orElse("id");
        List<Employer> unapproved = employerService.getUnapproved(sort, fieldName, searchField);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        Page<Employer> pageObj = employerService.findPaginated(PageRequest.of(currentPage - 1, pageSize), unapproved);
        int totalPages = pageObj.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("employer", pageObj);
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("user", userService.getAllUser("employer"));
        model.addAttribute("city", cityService.getAllAll());
        model.addAttribute("empTotal", unapproved);
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("sort", sort);
        model.addAttribute("fieldName", fieldName);
        model.addAttribute("searchField", searchField);
        model.addAttribute("fields", Arrays.asList("id", "user", "name", "email", "category", "city", "site",
                "description", "age", "amount", "size", "facebook", "instagram", "linkedIn", "twitter"));
        return "admin/approve/employer";
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
                                   @RequestParam(required = false) String link_twitter,
                                   @RequestParam Boolean approved) {
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
        emp.setApproved(approved);


        userService.addRole(userDB, Role.EMPLOYER);
        employerService.save(emp);
        return "redirect:/admin/approve/employer";
    }

    @GetMapping("/employer/delete/{id}")
    public String adminEmployerDelete(@PathVariable("id") long id) {
        Employer employer = employerService.findById(id);
        User user = employer.getUser();
        userService.removeRole(user, Role.EMPLOYER);
        employerService.delete(id);
        return "redirect:/admin/approve/employer";
    }

    @GetMapping("/seeker")
    public String adminSeekerPage(Model model,
                                  @RequestParam("sort") Optional<String> sortVal,
                                  @RequestParam(required = false) String fieldName,
                                  @RequestParam(required = false) String searchField,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        String sort = sortVal.orElse("id");
        List<Seeker> unapproved = seekerService.getUnapproved(sort, fieldName, searchField);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        Page<Seeker> pageObj = seekerService.findPaginated(PageRequest.of(currentPage - 1, pageSize), unapproved);
        int totalPages = pageObj.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("seeker", pageObj);
        model.addAttribute("category", categoryService.getAllAll());
        model.addAttribute("user", userService.getAllUser("seeker"));
        model.addAttribute("city", cityService.getAllAll());
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", unapproved);
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("sort", sort);
        model.addAttribute("fieldName", fieldName);
        model.addAttribute("searchField", searchField);
        model.addAttribute("fields", Arrays.asList("id", "user", "name", "email", "category", "city", "site",
                "description", "facebook", "instagram", "linkedIn", "twitter"));
        return "admin/approve/seeker";
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
                                 @RequestParam(required = false) String link_twitter,
                                 @RequestParam Boolean approved) {
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
        seek.setApproved(approved);
        cityService.addCities(seek, cityService.findByNames(city));
        seek.setDescription(description);

        userService.addRole(userDB, Role.SEEKER);
        seekerService.save(seek);
        return "redirect:/admin/approve/seeker";
    }

    @GetMapping("/seeker/delete/{id}")
    public String adminSeekerDelete(@PathVariable("id") long id) {
        Seeker seeker = seekerService.findById(id);
        User user = seeker.getUser();
        userService.removeRole(user, Role.SEEKER);
        seekerService.delete(id);
        return "redirect:/admin/approve/seeker";
    }

    @GetMapping("/file")
    public String filePage(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);
        Page<String> pageObj = storageService.findPaginated(PageRequest.of(currentPage - 1, pageSize), userService.getAllPaths(false));
        int totalPages = pageObj.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("paths", userService.getAllPaths(false).size());
        model.addAttribute("path", pageObj);
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        return "admin/approve/file";
    }

    @PostMapping("/file/approve")
    public String approveFile(@RequestParam String path) {
        String newFile = storageService.renameFile("/uploads/resumes/" + path, true);
        User user = userService.findUser(path.substring(0, path.indexOf("/")));
        String file = user.getFile();
        file = file.replace("/uploads/resumes/" + path.replace("\\", "/"), newFile.replace("\\", "/"));
        user.setFile(file);
        userService.save(user);
        return "redirect:/admin/approve/file";
    }

    @PostMapping("/file/delete")
    public String removeFile(@RequestParam String path) {
        storageService.removeFile("/uploads/resumes/" + path);
        User user = userService.findUser(path.substring(0, path.indexOf("/")));
        String file = user.getFile();
        file = file.replace("/uploads/resumes/" + path.replace("\\", "/"), "");
        user.setFile(file);
        userService.save(user);
        return "redirect:/admin/approve/file";
    }
}