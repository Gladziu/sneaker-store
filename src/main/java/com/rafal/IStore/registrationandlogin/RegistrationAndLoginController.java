package com.rafal.IStore.registrationandlogin;

import com.rafal.IStore.user.UserReceiverDto;
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
    public String registrationPage(Model model) {
        model.addAttribute("user", new UserReceiverDto());
        return "user/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserReceiverDto userReceiverDto, BindingResult bindingResult, Model model) {
        return registrationService.processRegistration(userReceiverDto, bindingResult, model);
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

}