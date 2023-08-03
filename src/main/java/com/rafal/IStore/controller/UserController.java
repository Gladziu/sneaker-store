package com.rafal.IStore.controller;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.UserRepository;
import com.rafal.IStore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "user/register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "/user/login";
    }


    // handler method to handle list of users ????????
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "adminview/userslist";
    }


    /*
    @PostMapping("/register")
    public String registerUser(AppUser appUser) {
        userRepository.save(appUser);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "user/register";
    }

    @PostMapping("/login")
    public String loginUser(AppUser appUser){
        boolean check = userService.checkAuthorisation(appUser.getUsername(), appUser.getPassword());
        if (check){
            return "redirect:/";
        }
        return "user/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/user/login";
    }
    */
}