package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;

public interface OrderService {
    public void saveOrder(OrderDto orderDto, String email);
}
