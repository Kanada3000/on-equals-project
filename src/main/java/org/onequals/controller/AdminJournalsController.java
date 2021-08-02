package org.onequals.controller;

import org.onequals.domain.Sticker;
import org.onequals.domain.Story;
import org.onequals.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;

@Controller
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminJournalsController {
    private final CategoryService categoryService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final TypeService typeService;
    private final CityService cityService;
    private final ResumeService resumeService;
    private final EmployerService employerService;
    private final SeekerService seekerService;
    private final StorageService storageService;
    private final PageService pageService;
    private final StoryService storyService;
    private final StickerService stickerService;

    public AdminJournalsController(CategoryService categoryService, VacancyService vacancyService, UserService userService, TypeService typeService, CityService cityService, ResumeService resumeService, EmployerService employerService, SeekerService seekerService, StorageService storageService, PageService pageService, StoryService storyService, StickerService stickerService) {
        this.categoryService = categoryService;
        this.vacancyService = vacancyService;
        this.userService = userService;
        this.typeService = typeService;
        this.cityService = cityService;
        this.resumeService = resumeService;
        this.employerService = employerService;
        this.seekerService = seekerService;
        this.storageService = storageService;
        this.pageService = pageService;
        this.storyService = storyService;
        this.stickerService = stickerService;
    }

    @GetMapping("/page/create")
    public String create() {
        return "admin/journals/generate";
    }

    @PostMapping("/page/create")
    public String pageCreate(@RequestParam("name") String title,
                             @RequestParam String data,
                             @RequestParam String shortData,
                             @RequestParam String label) throws IOException {
        org.onequals.domain.Page page = new org.onequals.domain.Page();
        page.setName(title);
        page.setFullBody(data);
        page.setShortBody(shortData);
        page.setLabel(label);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        page.setCreatedDate(ts);
        pageService.save(page);
        return switch (label) {
            case ("Шукачам") -> "redirect:/admin/journals/for-seeker";
            case ("Роботодавцям") -> "redirect:/admin/journals/for-employer";
            default -> "redirect:/admin/journals/legislation";
        };
    }

    @GetMapping("/page/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        org.onequals.domain.Page page = pageService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("data", page.getFullBody());
        model.addAttribute("shortData", page.getShortBody());
        model.addAttribute("label", page.getLabel());
        model.addAttribute("name", page.getName());
        return "admin/journals/edit";
    }

    @PostMapping("/page/edit/{id}")
    public String pageEdit(@PathVariable Long id,
                           @RequestParam("name") String title,
                           @RequestParam String data,
                           @RequestParam String shortData,
                           @RequestParam String label) {
        org.onequals.domain.Page page = pageService.getById(id);
        page.setName(title);
        page.setFullBody(data);
        page.setShortBody(shortData);
        page.setLabel(label);
        pageService.save(page);
        return switch (label) {
            case ("Шукачам") -> "redirect:/admin/journals/for-seeker";
            case ("Роботодавцям") -> "redirect:/admin/journals/for-employer";
            default -> "redirect:/admin/journals/legislation";
        };
    }

    @GetMapping("/page/{mode}/delete/{id}")
    public String adminPageDelete(@PathVariable String mode, @PathVariable("id") long id) {
        pageService.delete(id);
        return "redirect:/admin/journals/" + mode;
    }

    @GetMapping("/journals/for-seeker")
    public String forSeeker(Model model) {
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("mode", "seeker");
        model.addAttribute("page", pageService.getByLabel("Шукачам"));
        return "admin/journals/journals";
    }

    @GetMapping("/journals/for-employer")
    public String forEmployer(Model model) {
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("mode", "employer");
        model.addAttribute("page", pageService.getByLabel("Роботодавцям"));
        return "admin/journals/journals";
    }

    @GetMapping("/journals/legislation")
    public String legislation(Model model) {
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("mode", "legislation");
        model.addAttribute("page", pageService.getByLabel("Законодавство"));
        return "admin/journals/journals";
    }

    @GetMapping("/journals/history")
    public String story(Model model) {
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("story", storyService.getAll());
        return "admin/journals/story";
    }

    @GetMapping("/story/create")
    public String createStory() {
        return "admin/journals/generate-story";
    }

    @PostMapping("/story/create")
    public String addStory(@RequestParam MultipartFile image,
                           @RequestParam String data,
                           @RequestParam String name) throws IOException {
        Story story = new Story();
        story.setTitle(name);
        story.setText(data);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        story.setCreatedDate(ts);
        story.setImage(storageService.uploadImage(image, "stories"));
        storyService.save(story);
        return "redirect:/admin/journals/history";
    }

    @GetMapping("/story/edit/{id}")
    public String editStory(@PathVariable Long id, Model model) {
        Story story = storyService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("data", story.getText());
        model.addAttribute("title", story.getTitle());
        return "admin/journals/edit-story";
    }

    @PostMapping("/story/edit/{id}")
    public String storyEdit(@PathVariable Long id,
                            @RequestParam(required = false) MultipartFile file,
                            @RequestParam String title,
                            @RequestParam String data) throws IOException {
        Story story = storyService.getById(id);
        story.setTitle(title);
        story.setText(data);
        if (file != null){
            story.setImage(storageService.uploadImage(file, "stories"));
        }
        storyService.save(story);
        return "redirect:/admin/journals/history";
    }

    @GetMapping("/journals/slider")
    public String slider(Model model) {
        model.addAttribute("empTotal", employerService.getUnapproved());
        model.addAttribute("seekTotal", seekerService.getUnapproved());
        model.addAttribute("vacTotal", vacancyService.getUnapproved());
        model.addAttribute("resTotal", resumeService.getUnapproved());
        model.addAttribute("path", storageService.countFiles());
        model.addAttribute("sticker", stickerService.getAll());
        return "admin/journals/sticker";
    }

    @GetMapping("/sticker/create")
    public String createSticker() {
        return "admin/journals/generate-sticker";
    }

    @PostMapping("/sticker/create")
    public String addSticker(@RequestParam MultipartFile image,
                           @RequestParam String data,
                           @RequestParam String name) throws IOException {
        Sticker sticker = new Sticker();
        sticker.setTitle(name);
        sticker.setText(data);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        sticker.setCreatedDate(ts);
        sticker.setImage(storageService.uploadImage(image, "stickers"));
        stickerService.save(sticker);
        return "redirect:/admin/journals/slider";
    }

    @GetMapping("/sticker/edit/{id}")
    public String editSticker(@PathVariable Long id, Model model) {
        Sticker sticker = stickerService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("data", sticker.getText());
        model.addAttribute("title", sticker.getTitle());
        return "admin/journals/edit-sticker";
    }

    @PostMapping("/sticker/edit/{id}")
    public String stickerEdit(@PathVariable Long id,
                            @RequestParam(required = false) MultipartFile image,
                            @RequestParam String title,
                            @RequestParam String data) throws IOException {
        Sticker sticker = stickerService.getById(id);
        sticker.setTitle(title);
        sticker.setText(data);
        if (image != null){
            sticker.setImage(storageService.uploadImage(image, "stickers"));
        }
        stickerService.save(sticker);
        return "redirect:/admin/journals/slider";
    }
}