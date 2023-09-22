package com.rafal.IStore.controller;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.item.ItemOperation;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.BasketService;
import com.rafal.IStore.service.order.OrderService;
import com.rafal.IStore.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private BasketService basketService;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private OrderController orderController;

    @Test
    void testShowBasket() {
        //When
        String expectedResult = orderController.showBasket();

        //Then
        assertEquals("basket", expectedResult);
    }

    @Test
    void testIncreaseItem() {
        //Given
        Long itemId = 1L;
        int size = 40;

        //When
        String expectedResult = orderController.increaseItem(itemId, size);

        //Then
        verify(basketService, times(1)).itemOperation(itemId, size, ItemOperation.INCREASE);
        assertEquals("basket", expectedResult);
    }

    @Test
    void testDecreaseItem() {
        //Given
        Long itemId = 1L;
        int size = 40;

        //When
        String expectedResult = orderController.decreaseItem(itemId, size);

        //Then
        verify(basketService, times(1)).itemOperation(itemId, size, ItemOperation.DECREASE);
        assertEquals("basket", expectedResult);
    }

    @Test
    void testRemoveItemFromBasket() {
        //Given
        Long itemId = 1L;
        int size = 40;

        //When
        String expectedResult = orderController.removeItemFromBasket(itemId, size);

        //Then
        verify(basketService, times(1)).itemOperation(itemId, size, ItemOperation.REMOVE);
        assertEquals("basket", expectedResult);
    }

    @Test
    void testShowSummary_EmptyBusket() {
        //Given
        when(basketService.isBasketEmpty()).thenReturn(true);

        //When
        String expectedResult = orderController.showSummary(model, authentication);

        //Then
        verify(model, times(1)).addAttribute(eq("basketIsEmpty"), eq("Basket is empty!"));
        assertEquals("basket", expectedResult);
    }

    @Test
    void testShowSummary_NotEmptyBusket() {
        //Given
        User user = new User();
        user.setName("Jan Kowalski");

        when(basketService.isBasketEmpty()).thenReturn(false);
        when(userService.getCurrentUser(authentication)).thenReturn(user);

        //When
        String expectedResult = orderController.showSummary(model, authentication);

        //Then
        verify(model, times(1)).addAttribute(eq("name"), eq("Jan"));
        verify(model, times(1)).addAttribute(eq("surname"), eq("Kowalski"));
        verify(model, times(1)).addAttribute(eq("orderDto"), any(OrderDto.class));
        assertEquals("summary", expectedResult);
    }
    @Test
    void testSaveOrder_BindingResultErrors() {
        //Given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        OrderDto orderDto = new OrderDto();

        when(bindingResult.hasErrors()).thenReturn(true);

        //When
        String expectedResult = orderController.saveOrder(orderDto, bindingResult, authentication);

        //Then
        assertEquals("summary", expectedResult);
    }

    @Test
    void testSaveOrder_WithoutBindingResultErrors() {
        //Given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        OrderDto orderDto = new OrderDto();

        when(bindingResult.hasErrors()).thenReturn(false);

        User user = new User();

        when(userService.getCurrentUser(authentication)).thenReturn(user);

        //When
        String expectedResult = orderController.saveOrder(orderDto, bindingResult, authentication);

        //Then
        verify(orderService, times(1)).saveOrder(orderDto, user.getEmail());
        assertEquals("thanks-for-shopping", expectedResult);
    }

    @Test
    void testOrderHistory() {
        //Given
        User user = new User();
        user.setEmail("test@example.com");

        List<Order> orders = Collections.singletonList(new Order());
        when(userService.getCurrentUser(authentication)).thenReturn(user);
        when(orderRepository.findByEmail("test@example.com")).thenReturn(orders);

        //When
        String expectedResult = orderController.orderHistory(model, authentication);

        //Then
        verify(model, times(1)).addAttribute(eq("orders"), eq(orders));
        verify(model, times(1)).addAttribute(eq("items"), anyList());
        assertEquals("orders", expectedResult);
    }
}