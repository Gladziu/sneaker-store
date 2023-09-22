package com.rafal.IStore.controller;

import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.ItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.item.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemService itemService;

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
    void testShowOrders() {
        //When
        List<Order> orders = adminController.showOrders();

        //Then
        verify(orderRepository, times(1)).findAll();
        assertEquals(orders.size(), 0);
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
        verify(itemService, times(1)).editItem(1L, BigDecimal.valueOf(100.0), "Test Item", "test-image.jpg");
        assertEquals("redirect:/sneaker-store/home", expectedResult);
    }
}