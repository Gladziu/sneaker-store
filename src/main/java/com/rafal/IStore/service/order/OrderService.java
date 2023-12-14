package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.order.OrderHistory;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {
    void saveOrder(OrderDto orderDto, Authentication authentication);

    List<OrderHistory> historyOrders(Authentication authentication);
}
