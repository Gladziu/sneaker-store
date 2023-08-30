package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.mapper.OrderMapper;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.order.OrderItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.Basket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final Basket basket;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public void saveOrder(OrderDto orderDto, String email) {
        Order order = OrderMapper.mapToOrder(orderDto, basket, email);
        orderRepository.save(order);
        orderItemRepository.saveAll(OrderMapper.mapToOrderItemList(basket, order));
        basket.clearBasket();
    }
}
