package com.rafal.IStore.service.registration;

import com.rafal.IStore.dto.UserDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface RegistrationService {
    String processRegistration(UserDto userDto, BindingResult bindingResult, Model model);
}
