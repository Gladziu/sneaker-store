/*
package com.rafal.IStore.controller;

import com.rafal.IStore.admin.AdminController;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.item.ItemRepository;
import com.rafal.IStore.item.ItemService;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private Model model;
    @InjectMocks
    private AdminController adminController;

    @Test
    void testAdminPage() {
        //When
        String expectedResult = adminController.adminPage();

        //Then
        assertEquals("adminview/add-item", expectedResult);
    }


    @Test
    void testAddItem() {
        //Given
        Item item = new Item();

        //When
        String expectedResult = adminController.addItem(item);

        //Then
        verify(itemRepository, times(1)).save(item);
        assertEquals("redirect:/sneaker-store/home", expectedResult );
    }

    @Test
    void testUsers() {
        //Given
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto());

        when(userService.findAllUsers()).thenReturn(users);

        //When
        String expectedResult = adminController.users(model);

        //Then
        verify(model, times(1)).addAttribute("users", users);
        assertEquals("adminview/users-list", expectedResult);
    }
    @Test
    void testDeleteItem() {
        //Given
        Long itemId = 1L;

        //When
        String expectedResult = adminController.deleteItem(itemId);

        //Then
        verify(itemService, times(1)).deleteItem(itemId);
        assertEquals("redirect:/sneaker-store/home", expectedResult);
    }

    @Test
    void testEditItemForm() {
        //Given
        Model model = Mockito.mock(Model.class);
        Long itemId = 1L;
        Item item = new Item();

        when(itemService.findItemById(itemId)).thenReturn(item);

        //When
        String expectedResult = adminController.editItemForm(itemId, model);

        //Then
        verify(itemService, times(1)).findItemById(itemId);
        verify(model, times(1)).addAttribute("item", item);
        assertEquals("adminview/edit-item", expectedResult);
    }

    @Test
    void testEditItem() {
        //Given
        Item item = new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(100.0));
        item.setName("Test Item");
        item.setUrlImage("test-image.jpg");

        //When
        String expectedResult = adminController.editItem(item);

        //Then
        verify(itemService, times(1)).editItem(item);
        assertEquals("redirect:/sneaker-store/home", expectedResult);
    }
}*/
