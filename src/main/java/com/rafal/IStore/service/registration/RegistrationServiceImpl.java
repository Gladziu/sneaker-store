package com.rafal.IStore.service.registration;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;

    public RegistrationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String processRegistration(UserDto userDto, BindingResult bindingResult, Model model) {
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
}
