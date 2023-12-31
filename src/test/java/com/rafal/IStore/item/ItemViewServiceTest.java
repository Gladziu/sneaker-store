package com.rafal.IStore.item;

import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.shoppingbasket.BasketInfoService;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemViewServiceTest {
    @Mock
    private BasketInfoService basketInfoService;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private ItemViewServiceImpl itemViewService;

    @Test
    void populateItemsModel() {
        // Given
        Item item = new Item();
        Item item2 = new Item();
        List<Item> items = List.of(item, item2);
        when(itemService.getAllItems()).thenReturn(items);

        // When
        itemViewService.populateItemsModel(model, authentication);

        // Then
        verify(model, times(1)).addAttribute("items", items);
    }

    @Test
    void correctViewDueToRoleAdmin_IsAdminTrue() {
        // Given
        when(userService.isAdmin(authentication)).thenReturn(true);

        // When
        String result = itemViewService.correctViewDueToRole(model, authentication);

        // Then
        assertEquals("adminview/admin-home", result);
    }

    @Test
    void correctViewDueToRoleUser_IsAdminFalse() {
        // Given
        UserDto currentUser = UserDto.builder()
                .id(UUID.randomUUID())
                .firstName("Bob")
                .build();
        when(userService.isAdmin(authentication)).thenReturn(false);
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);

        // When
        String result = itemViewService.correctViewDueToRole(model, authentication);

        // Then
        assertEquals("home", result);
    }

}