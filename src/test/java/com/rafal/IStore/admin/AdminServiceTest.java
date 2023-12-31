package com.rafal.IStore.admin;

import com.rafal.IStore.item.ItemRepository;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.shoppingbasket.BasketItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BasketItemService basketItemService;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    void deleteItem() {
        // Given
        Long itemId = 1L;
        Item item = new Item();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // When
        adminService.deleteItem(itemId);

        // Then
        verify(basketItemService, times(1)).deleteBasketItems(item);
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void editItem() {
        // Given
        Item item = new Item();
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        // When
        adminService.editItem(item);

        // Then
        verify(basketItemService, times(1)).deleteBasketItems(item);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void addItem() {
        // Given
        Item item = new Item();

        // When
        adminService.addItem(item);

        // Then
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void findItemById() {
        // Given
        Long itemId = 1L;
        Item expectedItem = new Item();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(expectedItem));

        // When
        Item result = adminService.findItemById(itemId);

        // Then
        assertEquals(expectedItem, result);
    }
}