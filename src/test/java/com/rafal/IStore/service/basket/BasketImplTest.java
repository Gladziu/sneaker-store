package com.rafal.IStore.service.basket;

import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BasketImplTest {
    private BasketImpl basketUnderTest;
    @BeforeEach
    void setUp() {
        basketUnderTest = new BasketImpl();
    }
    @Test
    void canAddNewItem() {
        Item item = new Item("Sneakers", new BigDecimal("200"), null);
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);

        Item item2 = new Item("Nike", new BigDecimal("220"), null);
        item2.setId(101L);
        ItemWithSize itemWithSize2 = new ItemWithSize(item2, 44);
        basketUnderTest.addItem(itemWithSize2);

        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertEquals(new BigDecimal("200"), basketItems.get(0).getPrice());
        assertEquals(new BigDecimal("220"), basketItems.get(1).getPrice());
    }

    @Test
    void canAddAnotherItem() {
        Item item = new Item("Sneakers", new BigDecimal("200"), null);
        item.setId(100L);
        ItemWithSize itemWithSize = new ItemWithSize(item, 40);

        basketUnderTest.addItem(itemWithSize);
        basketUnderTest.addItem(itemWithSize);
        basketUnderTest.addItem(itemWithSize);
        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertEquals(3, basketItems.get(0).getCounter());
    }

    @Test
    void canRemoveItemAndMakeBasketEmpty() {
        Item item = new Item("Sneakers", new BigDecimal("200"), "urlImage");
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);

        basketUnderTest.removeItem(itemWithSize);

        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertTrue(basketItems.isEmpty());

        assertTrue(basketUnderTest.isBasketQuantityZero());
    }

    @Test
    void canRemoveJustOneItem() {
        Item item = new Item("Sneakers", new BigDecimal("200"), "urlImage");
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);
        basketUnderTest.addItem(itemWithSize);

        basketUnderTest.removeItem(itemWithSize);

        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertFalse(basketItems.isEmpty());

        assertFalse(basketUnderTest.isBasketQuantityZero());
    }


    @Test
    void canRemoveAllItemsOfOneType() {
        Item item = new Item("Sneakers", new BigDecimal("200"), "urlImage");
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);
        basketUnderTest.addItem(itemWithSize);
        basketUnderTest.addItem(itemWithSize);

        basketUnderTest.removeAllItems(itemWithSize);

        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertTrue(basketItems.isEmpty());

        assertTrue(basketUnderTest.isBasketQuantityZero());
    }

    @Test
    void canClearBasket() {
        Item item = new Item("Sneakers", new BigDecimal("200"), "urlImage");
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);

        basketUnderTest.clearBasket();

        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertTrue(basketItems.isEmpty());

        assertTrue(basketUnderTest.isBasketQuantityZero());
    }

    @Test
    void testIsBasketQuantityZero() {
        assertTrue(basketUnderTest.isBasketQuantityZero());
        Item item = new Item("Sneakers", new BigDecimal("200"), "urlImage");
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);

        assertFalse(basketUnderTest.isBasketQuantityZero());
    }

    @Test
    void testGetBasketItem() {
        Item item = new Item("Sneakers", new BigDecimal("200.09"), "urlImage");
        item.setId(100L);
        int size = 40;
        ItemWithSize itemWithSize = new ItemWithSize(item, size);
        basketUnderTest.addItem(itemWithSize);

        List<BasketItem> basketItems = basketUnderTest.getBasketItem();
        assertEquals("Sneakers", basketItems.get(0).getItemWithSize().getItem().getName());
    }

    @Test
    void testGetCounter() {

        assertEquals(0, basketUnderTest.getCounter());

        Item item = new Item("Sneakers Nike", new BigDecimal("200.09"), "urlImage");
        item.setId(100L);
        ItemWithSize itemWithSize = new ItemWithSize(item, 40);
        basketUnderTest.addItem(itemWithSize);

        Item item2 = new Item("Sneakers Adidas", new BigDecimal("145.49"), "urlImage");
        item2.setId(101L);
        ItemWithSize itemWithSize2 = new ItemWithSize(item2, 44);
        basketUnderTest.addItem(itemWithSize2);

        assertEquals(2, basketUnderTest.getCounter());
    }

    @Test
    void testGetSum() {

        assertEquals(new BigDecimal("0"), basketUnderTest.getSum());

        Item item = new Item("Sneakers Nike", new BigDecimal("200.09"), "urlImage");
        item.setId(100L);
        ItemWithSize itemWithSize = new ItemWithSize(item, 40);
        basketUnderTest.addItem(itemWithSize);

        Item item2 = new Item("Sneakers Adidas", new BigDecimal("145.49"), "urlImage");
        item2.setId(101L);
        ItemWithSize itemWithSize2 = new ItemWithSize(item2, 44);
        basketUnderTest.addItem(itemWithSize2);

        BigDecimal expectedSum = new BigDecimal("345.58");
        assertEquals(expectedSum, basketUnderTest.getSum());
    }

}