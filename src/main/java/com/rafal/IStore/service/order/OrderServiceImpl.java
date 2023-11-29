package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.mapper.OrderMapper;
import com.rafal.IStore.model.order.Order;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.order.OrderItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.Basket;
import com.rafal.IStore.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final Basket basket;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    public OrderServiceImpl(Basket basket, OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService) {
        this.basket = basket;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }


    @Override
    public void saveOrder(OrderDto orderDto, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        Order order = OrderMapper.mapToOrder(orderDto, basket, user.getEmail());
        orderRepository.save(order);
        orderItemRepository.saveAll(OrderMapper.mapToOrderItemList(basket, order));
        basket.clearBasket();
    }

    @Override
    public List<Order> ordersHistory(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        return orderRepository.findByEmail(user.getEmail());
    }
}
