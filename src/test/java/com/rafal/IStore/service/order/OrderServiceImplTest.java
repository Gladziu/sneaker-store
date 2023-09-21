package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.mapper.OrderMapper;
import com.rafal.IStore.model.item.Item;
import com.rafal.IStore.model.item.ItemWithSize;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.order.OrderItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.Basket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private Basket basket;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private OrderServiceImpl orderService;
    @Test
    void testSaveOrder() {
        //Given
        OrderDto orderDto = new OrderDto();

        when(basket.getSum()).thenReturn(BigDecimal.valueOf(100.0));

        //When
        orderService.saveOrder(orderDto, "test@example.com");

        //Then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(any(List.class));
        verify(basket, times(1)).clearBasket();
    }
}