package com.rafal.IStore.order;

import com.rafal.IStore.order.model.OrderHistory;
import com.rafal.IStore.shoppingbasket.BasketInfoService;
import com.rafal.IStore.shoppingbasket.model.BasketItem;
import com.rafal.IStore.shoppingbasket.BasketItemService;
import com.rafal.IStore.user.UserDto;
import com.rafal.IStore.user.model.User;
import com.rafal.IStore.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
class OrderServiceImpl implements OrderService {
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
    public List<OrderHistory> historyOrders(Authentication authentication) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        return orderRepository.findAllByEmail(currentUser.getEmail());
    }

    @Override
    public void saveOrder(OrderDto orderDto, Authentication authentication) {
        UserDto currentUser = userService.getCurrentUser(authentication);
        BigDecimal sum = basketInfoService.getSum(authentication);
        OrderHistory orderHistory = OrderMapper.mapToOrderHistory(orderDto, currentUser, sum);
        orderRepository.save(orderHistory);
        saveOrderedItems(authentication, orderHistory);
        clearBasketAfterPurchase(currentUser);
    }

    private void clearBasketAfterPurchase(UserDto currentUser) {
        basketItemService.clearBasket(currentUser.getId());
    }

    private void saveOrderedItems(Authentication authentication, OrderHistory orderHistory) {
        List<BasketItem> basketItemItems = basketItemService.getBasketItems(authentication);
        orderItemRepository.saveAll(OrderMapper.mapToOrderItems(orderHistory, basketItemItems));
    }
}
