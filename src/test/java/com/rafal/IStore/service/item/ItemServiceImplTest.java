package com.rafal.IStore.service.item;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemServiceImpl itemService;
    @Test
    void testDeleteItem() {
        //Given
        Long itemId = 1L;

        //When
        itemService.deleteItem(itemId);

        //Then
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void testEditItem() {
        //Given
        Long itemId = 1L;

        Item item = new Item();
        item.setId(itemId);
        item.setPrice(new BigDecimal("49.99"));
        item.setName("Old Item");
        item.setUrlImage("old-url");

        BigDecimal newPrice = new BigDecimal("99.99");
        String newName = "New Item";
        String newUrlImg = "new-url";

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //When
        itemService.editItem(itemId, newPrice, newName, newUrlImg);

        //Then
        assertEquals(newPrice, item.getPrice());
        assertEquals(newName, item.getName());
        assertEquals(newUrlImg, item.getUrlImage());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testFindItemById() {
        //Given
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //When
        Item itemExpected = itemService.findItemById(itemId);

        //Then
        assertEquals(itemId, itemExpected.getId());
    }

    @Test
    void testFindItemById_NotFound(){
        //Given
        Long itemId = 1L;

        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        //When
        Item itemExpected = itemService.findItemById(itemId);

        //Then
        assertNull(itemExpected);
    }
}