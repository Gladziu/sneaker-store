package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private Basket basket;
    @InjectMocks
    private BasketServiceImpl basketService;

    @Test
    public void testGetAllItems() {
        //Given
        Item item1 = new Item("Test", new BigDecimal("200"), null);
        Item item2 = new Item("Test2", new BigDecimal("220"), null);

        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        //When
        List<Item> items = basketService.getAllItems();

        //Then
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    public void testIsBasketEmpty() {
        //Given
        when(basket.isBasketQuantityZero()).thenReturn(true);

        //When
        boolean result = basketService.isBasketEmpty();

        //Then
        assertTrue(result);
    }

    @Test
    public void testItemOperationINCREASE() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //When
        basketService.itemOperation(itemId, size, ItemOperation.INCREASE);

        //Then
        verify(basket, times(1)).addItem(any(ItemWithSize.class));
    }

    @Test
    public void testItemOperationDECREASE() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //When
        basketService.itemOperation(itemId, size, ItemOperation.DECREASE);

        //Then
        verify(basket, times(1)).removeItem(any(ItemWithSize.class));
    }

    @Test
    public void testItemOperationREMOVE() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        //When
        basketService.itemOperation(itemId, size, ItemOperation.REMOVE);

        //Then
        verify(basket, times(1)).removeAllItems(any(ItemWithSize.class));
    }
}