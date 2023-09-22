package com.rafal.IStore.controller;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @Test
    void testShowRegistrationForm() {
        //When
        String expectedResult = userController.showRegistrationForm(model);

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
        String expectedResult = userController.registration(userDto, bindingResult, model);

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
        String expectedResult = userController.registration(userDto, bindingResult, model);

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
        String expectedResult = userController.registration(userDto, bindingResult, model);

        //Then
        verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());
        verify(model, times(1)).addAttribute(eq("user"), any(UserDto.class));
        assertEquals("/user/register", expectedResult);
    }

    @Test
    void testLogin() {
        //When
        String expectedResult = userController.login();

        //Then
        assertEquals("/user/login", expectedResult);
    }

    @Test
    void testUsers() {
        //Given
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto());

        when(userService.findAllUsers()).thenReturn(users);

        //When
        String expectedResult = userController.users(model);

        //Then
        verify(model, times(1)).addAttribute("users", users);
        assertEquals("adminview/users-list", expectedResult);
    }
}