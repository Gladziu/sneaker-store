package com.rafal.IStore.service.order;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.service.basket.BasketInfoService;
import com.rafal.IStore.service.basket.BasketItemService;
import com.rafal.IStore.service.item.ItemService;
import com.rafal.IStore.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class OrderViewServiceImpl implements OrderViewService {
    private static final int ZERO_ITEMS_IN_BASKET = 0;
    private final OrderService orderService;
    private final UserService userService;
    private final BasketItemService basketItemService;
    private final BasketInfoService basketInfoService;
    private final ItemService itemService;


    public OrderViewServiceImpl(OrderService orderService, UserService userService, BasketItemService basketItemService, BasketInfoService basketInfoService, ItemService itemService) {
        this.orderService = orderService;
        this.userService = userService;
        this.basketItemService = basketItemService;
        this.basketInfoService = basketInfoService;
        this.itemService = itemService;
    }

    @Override
    public void populateSumQuantityAndBasketItems(Model model, Authentication authentication) {
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));
        model.addAttribute("basketItems", basketItemService.getBasketItems(authentication));
    }

    @Override
    public void handleOrderValidationErrors(Model model, Authentication authentication) {
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));
        model.addAttribute("basketItems", basketItemService.getBasketItems(authentication));
    }

    @Override
    public void handleEmptyBasket(Model model, Authentication authentication) {
        model.addAttribute("sum", ZERO_ITEMS_IN_BASKET);
        model.addAttribute("quantity", ZERO_ITEMS_IN_BASKET);
        model.addAttribute("basketIsEmpty", "Basket is empty!");
    }

    @Override
    public void populateSummaryModel(Model model, Authentication authentication) {
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("basketItems", basketItemService.getBasketItems(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));

        User user = userService.getCurrentUser(authentication);
        model.addAttribute("name", user.getFirstName());
        model.addAttribute("surname", user.getLastName());
        model.addAttribute("orderDto", new OrderDto());
    }

    @Override
    public void populateOrderHistoryModel(Model model, Authentication authentication) {
        model.addAttribute("orders", orderService.historyOrders(authentication));
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));
    }
}
