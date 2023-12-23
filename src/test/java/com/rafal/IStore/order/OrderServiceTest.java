package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import com.rafal.IStore.shoppingbasket.BasketInfoService;
import com.rafal.IStore.shoppingbasket.BasketItemService;
import com.rafal.IStore.user.UserService;
import com.rafal.IStore.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    @InjectMocks
    private OrderServiceImpl orderService;
    private User user;
    private List<OrderHistory> historyOrders;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");

        historyOrders = new ArrayList<>();  // Inicjalizacja listy
        OrderHistory orderHistory1 = OrderHistory.builder()
                .email("test@example.com")
                .build();
        historyOrders.add(orderHistory1);

        OrderHistory orderHistory2 = OrderHistory.builder()
                .email("test@example.com")
                .build();
        historyOrders.add(orderHistory2);
    }

/*    @Test
    void historyOrders_ShouldReturnOrderHistoryList_WhenUserIsAuthenticated() {
        //given
        Authentication authentication = mock(Authentication.class);
        when(userService.getCurrentUser(authentication)).thenReturn(user);
        String email = user.getEmail();
        when(orderRepository.findAllByEmail(email)).thenReturn(historyOrders);

        //when
        List<OrderHistory> result = orderService.historyOrders(authentication);

        //then
        assertThat(result, containsInAnyOrder(historyOrders.toArray()));
    }*/

    @Test
    void saveOrder() {
    }
}