package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import org.springframework.security.core.Authentication;

import java.util.List;

interface OrderService {
    List<OrderHistory> historyOrders(Authentication authentication);

    void saveOrder(OrderDto orderDto, Authentication authentication);
}
