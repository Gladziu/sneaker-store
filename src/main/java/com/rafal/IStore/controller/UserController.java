package com.rafal.IStore.controller;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
        if (userService.hasUserExists(userDto)) {
            bindingResult.rejectValue("email", String.valueOf(HttpStatus.CONFLICT),
                    "There is already an account registered with the same email");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/user/register";
        }
        userService.saveUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

}