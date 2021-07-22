package org.onequals.controller;

import org.onequals.domain.*;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin/journals")
public class AdminJournalController {
    private final CategoryService categoryService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;
    private final ResumeService resumeService;
    private final EmployerService employerService;
    private final SeekerService seekerService;
    private final StorageService storageService;

    public AdminJournalController(CategoryService categoryService, VacancyService vacancyService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService, EmployerService employerService, SeekerService seekerService, StorageService storageService) {
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

    @GetMapping("/for-seeker")
    public String forSeeker() {
        return "admin/journals/for-seeker";
    }

    @GetMapping("/generation")
    public String generation() {
        return "admin/journals/generate";
    }
}