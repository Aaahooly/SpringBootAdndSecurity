package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.util.UserValidation;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class RegistrationController {


    private final RegistrationService registrationService;
    private final UserValidation userValidation;

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserValidation userValidation) {
        this.registrationService = registrationService;
        this.userValidation = userValidation;
    }

    @GetMapping()
    public String loginPage() {
        return "auth/login_bt";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user,
                                      BindingResult bindingResult) {
        System.out.println(user.toString());
        userValidation.validate(user,bindingResult);
        if(bindingResult.hasErrors()) return "/auth/registration";
        registrationService.registration(user);
        return "redirect:/auth";
    }
}

