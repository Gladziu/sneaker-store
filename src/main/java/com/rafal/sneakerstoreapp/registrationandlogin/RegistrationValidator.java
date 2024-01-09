package com.rafal.sneakerstoreapp.registrationandlogin;

import com.rafal.sneakerstoreapp.user.UserReceiverDto;
import com.rafal.sneakerstoreapp.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
class RegistrationValidator {

    private final UserService userService;

    public RegistrationValidator(UserService userService) {
        this.userService = userService;
    }

    public void emailDuplicationValidation(UserReceiverDto userReceiverDto, BindingResult bindingResult) {
        if (userService.hasUserExists(userReceiverDto)) {
            bindingResult.rejectValue("email", String.valueOf(HttpStatus.CONFLICT),
                    "There is already an account registered with the same email");
        }
    }

    public void passwordStrengthValidation(UserReceiverDto userReceiverDto, BindingResult bindingResult) {
        if (userReceiverDto.getPassword().length() < 9) {
            bindingResult.rejectValue("password", String.valueOf(HttpStatus.CONFLICT),
                    "Password must be at least 9 characters long");
        }
    }

}