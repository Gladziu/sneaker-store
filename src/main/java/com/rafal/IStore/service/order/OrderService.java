package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.mapper.OrderMapper;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.repository.order.OrderItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final Basket basket;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(Basket basket, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.basket = basket;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void saveOrder(OrderDto orderDto) {
        Order order = OrderMapper.mapToOrder(orderDto, basket);
        orderRepository.save(order);
        orderItemRepository.saveAll(OrderMapper.mapToOrderItemList(basket, order));
        basket.clearBasket();
    }
}
