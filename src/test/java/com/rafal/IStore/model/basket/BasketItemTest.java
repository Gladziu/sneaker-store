package com.rafal.IStore.model.basket;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


class BasketItemTest {

    private BasketItem basketItem;
    private ItemWithSize itemWithSize;
    @BeforeEach
    public void setUp(){
        Item item = new Item("test", BigDecimal.valueOf(100.00), "urlImage");
        item.setId(1L);
        itemWithSize = new ItemWithSize(item, 40);
        basketItem = new BasketItem(itemWithSize);
    }

    @Test
    void testIncreaseCounter() {
        //Given
        basketItem.increaseCounter();

        //When
        int expectedCount = basketItem.getCounter();

        //Then
        assertEquals(2, expectedCount);
    }

    @Test
    void testDecreaseCounter() {
        //Given
        basketItem.decreaseCounter();

        //When
        int expectedCount = basketItem.getCounter();

        //Then
        assertEquals(0, expectedCount);
    }

    @Test
    void testHasZeroItems_WithoutItems() {
        //Given
        basketItem.decreaseCounter();

        //When
        boolean result = basketItem.hasZeroItems();

        //Then
        assertTrue(result);
    }

    @Test
    void testHasZeroItems_WithItems() {
        //When
        boolean result = basketItem.hasZeroItems();

        //Then
        assertFalse(result);
    }

    @Test
    void testIdEquals_SameItem() {
        //When
        ItemWithSize sameItemWithSize = new ItemWithSize(itemWithSize.getItem(), itemWithSize.getSize());

        //Then
        assertTrue(basketItem.idEquals(sameItemWithSize));
    }

    @Test
    void testIdEquals_DifferentItem() {
        //Given
        Item item2 = new Item("test", new BigDecimal("200.99"), "url");
        item2.setId(2L);

        //When
        ItemWithSize differentItemWithSize = new ItemWithSize(item2, 46);

        //Then
        assertFalse(basketItem.idEquals(differentItemWithSize));
    }
}