package com.rafal.sneakerstoreapp.order;

import com.rafal.sneakerstoreapp.order.model.OrderHistory;
import org.springframework.security.core.Authentication;

import java.util.List;

interface OrderService {
    List<OrderHistory> historyOrders(Authentication authentication);

    void saveOrder(OrderDto orderDto, Authentication authentication);
}
