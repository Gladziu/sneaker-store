package com.rafal.IStore.registrationandlogin;

import com.rafal.IStore.user.UserReceiverDto;
import com.rafal.IStore.user.UserService;
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

}