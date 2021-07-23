package org.onequals.controller;

import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.services.CategoryService;
import org.onequals.services.CityService;
import org.onequals.services.StorageService;
import org.onequals.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
public class PageController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final StorageService storageService;

    public PageController(UserService userService, CategoryService categoryService, CityService cityService, StorageService storageService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("category", categoryService.getAll());
        model.addAttribute("city", cityService.getAll());
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
            inputStream = new FileInputStream("/uploads/resumes/" + path);
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

//    @PostMapping("/image/upload")
//    public String upload(@RequestPart MultipartFile upload, @RequestParam(value = "CKEditorFuncNum") String callback, HttpServletRequest request) throws IOException {
//        System.out.println(upload.getOriginalFilename());
//        storageService.uploadFileCKEditor(upload);
//        String imgUrl = request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort())).concat(uploadUri).concat(destFileName);
//        StringBuffer sb = new StringBuffer();
//        sb.append("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(");
//        sb.append(callback);
//        sb.append(",'");
//        sb.append(imgUrl);
//        sb.append("','image uploaded successfully!!')</script>");
//        return sb.toString();
//        return sb.toString();
//    }

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

    @GetMapping("/result")
    public String ckeditor(){
        return "empty";
    }
}