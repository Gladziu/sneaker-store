/*
package com.rafal.IStore.controller;

import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.model.User;
import com.rafal.IStore.registrationandlogin.RegistrationAndLoginController;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationAndLoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private RegistrationAndLoginController registrationAndLoginController;

    @Test
    void testShowRegistrationForm() {
        //When
        String expectedResult = registrationAndLoginController.showRegistrationForm(model);

        //Then
        verify(model, times(1)).addAttribute(eq("user"), any(UserDto.class));
        assertEquals("user/register", expectedResult);
    }

    @Test
    void testRegistration_ExistingEmail() {
        //Given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userService.findUserByEmail(userDto.getEmail())).thenReturn(new User());
        when(bindingResult.hasErrors()).thenReturn(true);

        //When
        String expectedResult = registrationAndLoginController.registration(userDto, bindingResult, model);

        //Then
        verify(bindingResult, times(1))
                .rejectValue(eq("email"), isNull()
                        ,eq("There is already an account registered with the same email"));
        verify(model, times(1)).addAttribute(eq("user"), any(UserDto.class));
        assertEquals("/user/register", expectedResult);
    }

    @Test
    void testRegistration_NewEmail() {
        //Given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userService.findUserByEmail(userDto.getEmail())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        //When
        String expectedResult = registrationAndLoginController.registration(userDto, bindingResult, model);

        //Then
        verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());
        verify(userService, times(1)).saveUser(userDto);
        assertEquals("redirect:/login", expectedResult);
    }

    @Test
    void testRegistration_ValidationErrors() {
        //Given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userService.findUserByEmail(userDto.getEmail())).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(true);

        //When
        String expectedResult = registrationAndLoginController.registration(userDto, bindingResult, model);

        //Then
        verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());
        verify(model, times(1)).addAttribute(eq("user"), any(UserDto.class));
        assertEquals("/user/register", expectedResult);
    }

    @Test
    void testLogin() {
        //When
        String expectedResult = registrationAndLoginController.login();

        //Then
        assertEquals("/user/login", expectedResult);
    }

}*/
