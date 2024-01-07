package com.rafal.IStore.registrationandlogin;

import com.rafal.IStore.user.UserReceiverDto;
import com.rafal.IStore.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
class RegistrationService {

    private final UserService userService;
    private final RegistrationValidator registrationValidator;

    public RegistrationService(UserService userService, RegistrationValidator registrationValidator) {
        this.userService = userService;
        this.registrationValidator = registrationValidator;
    }


    public String processRegistration(UserReceiverDto userReceiverDto, BindingResult bindingResult, Model model) {
        registrationValidator.passwordStrengthValidation(userReceiverDto, bindingResult);
        registrationValidator.emailDuplicationValidation(userReceiverDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userReceiverDto);
            return "/user/register";
        }
        userService.saveUserBasedOnRole(userReceiverDto);
        return "redirect:/login";
    }
}
