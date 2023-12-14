package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.mapper.OrderMapper;
import com.rafal.IStore.model.basket.BasketItem;
import com.rafal.IStore.model.order.OrderHistory;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.order.OrderItemRepository;
import com.rafal.IStore.repository.order.OrderRepository;
import com.rafal.IStore.service.basket.BasketInfoService;
import com.rafal.IStore.service.basket.BasketItemService;
import com.rafal.IStore.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final BasketItemService basketItemService;
    private final BasketInfoService basketInfoService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService, BasketItemService basketItemService, BasketInfoService basketInfoService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.basketItemService = basketItemService;
        this.basketInfoService = basketInfoService;
    }

    @Override
    public void saveOrder(OrderDto orderDto, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        BigDecimal sum = basketInfoService.getSum(authentication);
        OrderHistory orderHistory = OrderMapper.mapToOrder(orderDto, user, sum);
        orderRepository.save(orderHistory);
        List<BasketItem> basketItemItems = basketItemService.getBasketItems(authentication);
        orderItemRepository.saveAll(OrderMapper.mapToOrderItemList(orderHistory, basketItemItems));
        basketItemService.clearBasket(user);
    }

    @Override
    public List<OrderHistory> historyOrders(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        return orderRepository.findByEmail(user.getEmail());
    }
}
