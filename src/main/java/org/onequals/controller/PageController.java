package org.onequals.controller;

import org.apache.commons.io.FileUtils;
import org.onequals.domain.Page;
import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.services.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
public class PageController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final PageService pageService;
    private final StorageService storageService;
    private final ServletContext servletContext;
    private final StoryService storyService;
    private final StickerService stickerService;

    public PageController(UserService userService, CategoryService categoryService, CityService cityService, PageService pageService, StorageService storageService, ServletContext servletContext, StoryService storyService, StickerService stickerService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.pageService = pageService;
        this.storageService = storageService;
        this.servletContext = servletContext;
        this.storyService = storyService;
        this.stickerService = stickerService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
        model.addAttribute("sticker", stickerService.getAll());
        return "index";
    }

    @GetMapping("/login")
    public String signInPage(Model model) {
        return "log-in";
    }

    @GetMapping("/login-error")
    public String signInErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "log-in";
    }

    @GetMapping("/list")
    public String toList(Principal principal) {
        if (principal != null) {
            User user = userService.findUser(principal.getName());
            if (user.getRoles().contains(Role.EMPLOYER)) {
                return "redirect:/resume/list";
            } else {
                return "redirect:/vacancy/list";
            }
        }
        return "redirect:/vacancy/list";
    }

    @GetMapping("/trick/admin/activated/true")
    public String activatedAdmin(){
        userService.activatedAdmin();
        return "redirect:/";
    }

    @PostMapping("/download")
    public void downloadFile(@RequestParam String path,
                             HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        if(path.contains("uploads")){
            path = path.substring(17);
        }
        String headerValue = "attachment; filename=\"resume_" + path.substring(0, path.lastIndexOf('\\')) +".pdf\"";
        response.setHeader(headerKey, headerValue);
        FileInputStream inputStream;
        try {
            File file = new File("/uploads/resumes/" + path);
            inputStream = new FileInputStream(file);
            try {
                int c;
                while ((c = inputStream.read()) != -1) {
                    response.getWriter().write(c);
                }
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                response.getWriter().close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @GetMapping("/uploads/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("/uploads/images/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/uploads/images/stories/{filename}")
    public ResponseEntity<byte[]> getImageStory(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("/uploads/images/stories/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/uploads/images/stickers/{filename}")
    public ResponseEntity<byte[]> getImageSticker(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File("/uploads/images/stickers/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/pages/{id}")
    public String loadPage(@PathVariable Long id, Model model){
        Page page = pageService.getById(id);
        model.addAttribute("name", page.getName());
        model.addAttribute("body", page.getFullBody());
        return "template";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "errors/403";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy(){
        return "privacy-policy";
    }
    @GetMapping("/tou")
    public String tou(){
        return "tou";
    }

    @GetMapping("/about")
    public String aboutUs()
    {
        return "about-us";
    }

    @GetMapping("/journal/seeker")
    public String journalSeeker(Model model)
    {
        model.addAttribute("page", pageService.getByLabel("Шукачам"));
        model.addAttribute("story", storyService.getAll());
        return "for-seeker";
    }

    @GetMapping("/journal/employer")
    public String journalEmployer(Model model)
    {
        model.addAttribute("page", pageService.getByLabel("Роботодавцям"));
        return "for-employer";
    }

    @GetMapping("/journal/legislation")
    public String journalLegislation(Model model)
    {
        model.addAttribute("page", pageService.getByLabel("Законодавство"));
        return "legislation";
    }
}