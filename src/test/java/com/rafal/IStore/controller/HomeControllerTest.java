/*
package com.rafal.IStore.controller;

import com.rafal.IStore.item.controller.ItemController;
import com.rafal.IStore.shoppingbasket.BasketOperation;
import com.rafal.IStore.user.model.User;
import com.rafal.IStore.shoppingbasket.BasketService;
import com.rafal.IStore.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
    @Mock
    private BasketService basketService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private ItemController homeController;

    @Test
    void testHome_RoleIsNotAdmin() {
        //Give
        Authentication authentication = Mockito.mock(Authentication.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);

        User user = new User();

        when(userService.getCurrentUser(authentication)).thenReturn(user);
        when(userService.roleMatching("ROLE_ADMIN", user.getEmail())).thenReturn(false);
        //When
        String expectedResult = homeController.home(model, authentication);

        //Then
        verify(model, times(1)).addAttribute(eq("items"), any());
        assertEquals("home", expectedResult);
    }

    @Test
    void testHome_RoleIsAdmin() {
        //Give
        Authentication authentication = Mockito.mock(Authentication.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);

        User user = new User();

        when(userService.getCurrentUser(authentication)).thenReturn(user);
        when(userService.roleMatching("ROLE_ADMIN", user.getEmail())).thenReturn(true);

        //When
        String expectedResult = homeController.home(model, authentication);


        //Then
        verify(model, times(1)).addAttribute(eq("items"), any());
        assertEquals("adminview/admin-home", expectedResult);
    }

    @Test
    void testAddItemToBasket() {
        //Given
        Long itemId = 1L;
        int size = 40;

        //When
        String expectedResult = homeController.addItemToBasket(itemId, size, model);

        //Then
        verify(basketService, times(1)).itemOperation(itemId, size, BasketOperation.INCREASE);
        verify(model, times(1)).addAttribute(eq("items"), any());
        assertEquals("redirect:/sneaker-store/home", expectedResult);
    }
}*/
