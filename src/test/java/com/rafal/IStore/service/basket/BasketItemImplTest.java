/*
package com.rafal.IStore.service.basket;

import com.rafal.IStore.shoppingbasket.model.BasketItem;
import com.rafal.IStore.item.model.Item;
import com.rafal.IStore.item.model.ItemWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BasketItemImplTest {

    @InjectMocks
    private BasketImpl basket;

    @Test
    void testAddNewItem() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertEquals(new BigDecimal("200"), basketItems.get(0).getPrice());
    }

    @Test
    void testAddAnotherItemOfOneType() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);
        basket.addItem(itemWithSize);

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertEquals(2, basketItems.get(0).getCounter());
    }

    @Test
    void testRemoveItem() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);
        basket.removeItem(itemWithSize);

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertTrue(basketItems.isEmpty());
    }

    @Test
    void testRemoveOneItem() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);
        basket.addItem(itemWithSize);

        basket.removeItem(itemWithSize);

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertFalse(basketItems.isEmpty());
    }


    @Test
    void testRemoveAllItemsAtOnce() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);
        basket.addItem(itemWithSize);
        basket.addItem(itemWithSize);

        basket.removeAllItems(itemWithSize);

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertTrue(basketItems.isEmpty());
    }

    @Test
    void testClearBasket() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        Long itemId2 = 2L;
        int size2 = 44;
        Item item2 = new Item("Test2", new BigDecimal("222"), null);
        item.setId(itemId2);
        ItemWithSize itemWithSize2 = new ItemWithSize(item2, size2);

        basket.addItem(itemWithSize);
        basket.addItem(itemWithSize);
        basket.addItem(itemWithSize2);

        basket.clearBasket();

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertTrue(basketItems.isEmpty());
    }

    @Test
    void testIsBasketQuantityZero_WithItemsInBasket() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basket.addItem(itemWithSize);

        //When
        boolean result = basket.isBasketQuantityZero();

        //Then
        assertFalse(result);
    }

    @Test
    void testIsBasketQuantityZero_WithEmptyBasket() {
        //Given


        //When
        boolean result = basket.isBasketQuantityZero();

        //Then
        assertTrue(result);
    }



    @Test
    void testGetBasketItem() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);

        //When
        List<BasketItem> basketItems = basket.getBasketItem();

        //Then
        assertEquals("Test", basketItems.get(0).getItemWithSize().getItem().getName());
    }

    @Test
    void testGetCounter_NotEmptyBasket() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);

        //When
        int expectedCounter = 1;

        //Then
        assertEquals(expectedCounter, basket.getCounter());
    }

    @Test
    void testGetCounter_WithEmptyBasket() {
        //Given

        //When
        int expectedCounter = basket.getCounter();

        //Then
        assertEquals(expectedCounter, basket.getCounter());
    }

    @Test
    void testGetSum_WithNotEmptyBasket() {
        //Given
        Long itemId = 1L;
        int size = 40;
        Item item = new Item("Test", new BigDecimal("200.22"), null);
        item.setId(itemId);
        ItemWithSize itemWithSize = new ItemWithSize(item, size);

        basket.addItem(itemWithSize);

        //When
        BigDecimal expectedSum = new BigDecimal("200.22");

        //Then
        assertEquals(expectedSum, basket.getSum());
    }

    @Test
    void testGetSum_WithEmptyBasket() {
        //Given

        //When
        BigDecimal expectedSum = BigDecimal.ZERO;

        //Then
        assertEquals(expectedSum, basket.getSum());
    }
}*/
