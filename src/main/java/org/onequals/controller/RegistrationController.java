package org.onequals.controller;

import org.onequals.domain.Role;
import org.onequals.domain.User;
import org.onequals.recaptcha.ReCaptchaResponse;
import org.onequals.services.ReCaptchaRegisterService;
import org.onequals.services.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final ReCaptchaRegisterService reCaptchaRegisterService;

    public RegistrationController(UserService userService, OAuth2AuthorizedClientService authorizedClientService, ReCaptchaRegisterService reCaptchaRegisterService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
        this.reCaptchaRegisterService = reCaptchaRegisterService;
    }

    @GetMapping("/register")
    public String registration() {
        return "sign-in";
    }

    @PostMapping("/register")
    public String addUser(Model model,
                          @RequestParam("password") String pas,
                          @RequestParam("name") String name,
                          @RequestParam("username") String username,
                          @RequestParam("g-recaptcha-response") String response) {
        ReCaptchaResponse reCaptchaResponse = reCaptchaRegisterService.verify(response);

        if (!reCaptchaResponse.isSuccess()) {
            return "error";
        }

        User userFromDb = userService.findUser(username);

        if (userFromDb != null) {
            model.addAttribute("message", "Такий користувач вже існує!");
            return "sign-in";
        }

        User user = new User();

        user.setName(name);
        user.setUsername(username);
        user.setPassword(userService.setPassword(pas));
        user.setLink(userService.getRandomLink());
        user.setActivated(true);
        userService.save(user);
        userService.auth(user);


//        String message = String.format(
//                "Привіт, %s! \n" +
//                        "Ласкаво просимо до нашої спільноти OnEquals. \n" +
//                        "Будь ласка, перейдіть за посиланням для підтвердження електронної пошти: " +
//                        "https://onequals.com.ua/register/activate/%s",
//                user.getName(),
//                user.getLink());
//        userService.sendEmail(user, "Код для активації акаунта", message);
        return "sign-in-2";
    }

    @GetMapping("/choose")
    public String choose() {
        return "sign-in-2";
    }

    @GetMapping("/register/activate/{link}")
    public String activate(@PathVariable("link") String link) {
        User user = userService.getByLink(link);
        user.setActivated(true);
        userService.save(user);
        userService.auth(user);
        return "redirect:/sign-in-2/";
    }

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (!userInfoEndpointUri.isEmpty()) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            String username = (String) userAttributes.get("email");
            String name = (String) userAttributes.get("name");
            User user = new User();
            if (userService.findUser(username) == null) {
                user.setUsername(username);
                user.setRoles(Collections.singleton(Role.USER));
                user.setActivated(true);
                user.setName(name);
                user.setPassword(userService.setPassword(userService.randomPassword()));
                userService.save(user);
                userService.auth(user);
            } else {
                user = userService.findUser(username);
                userService.auth(user);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/register/password")
    public String forgotPassword() {
        return "email";
    }

    @PostMapping("/sendPassword")
    public String sendPassword(@RequestParam String username) {
        User user = userService.findUser(username);
        String password = userService.getRandomLink();
        user.setPassword(password);
        String message = String.format(
                "Привіт, %s! \n" +
                        "Схоже, Ви забули пароль. \n" +
                        "Для його відновлення - перейдіть за посиланням: " +
                        "https://onequals.com.ua/activatePassword/%s/%s",
                user.getName(),
                user.getId(),
                password);
        userService.sendEmail(user, "Відновлення пароля", message);
        return "alert-password";
    }

    @GetMapping("/activatePassword/{id}/{password}")
    public String activatePassword(@PathVariable String password,
                                   @PathVariable Long id,
                                   Model model) {
        User user = userService.findById(id).get();
        if (user.getPassword().equals(password)) {
            model.addAttribute("userId", id);
            return "new-password";
        } else {
            return "redirect:/403";
        }
    }

    @PostMapping("acceptPassword")
    public String newPassword(@RequestParam Long id,
                              @RequestParam String password) {
        User user = userService.findById(id).get();
        user.setPassword(userService.setPassword(password));
        userService.save(user);
        return "redirect:/login";
    }
}