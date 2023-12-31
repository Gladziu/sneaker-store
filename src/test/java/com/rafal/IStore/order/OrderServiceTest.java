package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import com.rafal.IStore.shoppingbasket.BasketInfoService;
import com.rafal.IStore.shoppingbasket.BasketItemService;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.UserService;
import com.rafal.IStore.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private UserService userService;
    @Mock
    private BasketItemService basketItemService;
    @Mock
    private BasketInfoService basketInfoService;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void historyOrders_ExistingOrderHistory() {
        // Given
        UserDto currentUser = UserDto.builder()
                .id(UUID.randomUUID())
                .build();

        OrderHistory orderHistory = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("bob@example.com")
                .build();

        OrderHistory orderHistory2 = OrderHistory.builder()
                .id(UUID.randomUUID())
                .email("tom@example.com")
                .build();

        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);

        List<OrderHistory> expectedOrderHistory = List.of(orderHistory, orderHistory2);
        when(orderRepository.findAllByEmail(currentUser.getEmail())).thenReturn(expectedOrderHistory);

        // When
        List<OrderHistory> result = orderService.historyOrders(authentication);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    void saveOrder() {
        // Given
        OrderDto orderDto = new OrderDto();
        BigDecimal sum = BigDecimal.TEN;
        UserDto currentUser = UserDto.builder()
                .id(UUID.randomUUID())
                .build();

        when(basketInfoService.getSum(authentication)).thenReturn(sum);
        when(userService.getCurrentUser(authentication)).thenReturn(currentUser);

        // When
        orderService.saveOrder(orderDto, authentication);

        // Then
        verify(orderRepository, times(1)).save(any(OrderHistory.class));
        verify(orderItemRepository, times(1)).saveAll(anyList());
        verify(basketItemService, times(1)).clearBasket(currentUser.getId());
    }
}