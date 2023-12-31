package com.rafal.IStore.registrationandlogin;

import com.rafal.IStore.user.UserReceiverDto;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private RegistrationValidator registrationValidator;

    @Mock
    private UserReceiverDto userReceiverDto;
    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void processRegistration_EmailDuplicationValidationFails_ReturnsRegisterPage() {
        // given
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        String result = registrationService.processRegistration(userReceiverDto, bindingResult, model);

        // then
        verify(registrationValidator, times(1)).emailDuplicationValidation(userReceiverDto, bindingResult);
        verify(model, times(1)).addAttribute(eq("user"), any(UserReceiverDto.class));
        assertThat(result).isEqualTo("/user/register");
    }

    @Test
    void processRegistration_EmailDuplicationValidationPasses_SaveUserAndRedirectToLoginPage() {
        // given
        when(bindingResult.hasErrors()).thenReturn(false);

        // when
        String result = registrationService.processRegistration(userReceiverDto, bindingResult, model);

        // then
        verify(registrationValidator, times(1)).emailDuplicationValidation(userReceiverDto, bindingResult);
        verify(userService, times(1)).saveUser(userReceiverDto);
        assertThat(result).isEqualTo("redirect:/login");
    }
}