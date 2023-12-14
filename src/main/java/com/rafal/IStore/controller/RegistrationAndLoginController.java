package com.rafal.IStore.controller;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.service.registration.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationAndLoginController {

    private final RegistrationService registrationService;

    public RegistrationAndLoginController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        return registrationService.processRegistration(userDto, bindingResult, model);
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

}