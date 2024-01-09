package com.rafal.sneakerstoreapp.order;

import com.rafal.sneakerstoreapp.item.ItemService;
import com.rafal.sneakerstoreapp.shoppingbasket.BasketInfoService;
import com.rafal.sneakerstoreapp.shoppingbasket.BasketItemService;
import com.rafal.sneakerstoreapp.user.UserDto;
import com.rafal.sneakerstoreapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
class OrderViewServiceImpl implements OrderViewService {
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
    public String processSavingOrder(OrderDto orderDto, BindingResult bindingResult, Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            populateSumQuantityAndBasketItems(model, authentication);
            return "summary";
        }
        orderService.saveOrder(orderDto, authentication);
        return "thanks-for-shopping";
    }

    @Override
    public void populateSumQuantityAndBasketItems(Model model, Authentication authentication) {
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));
        model.addAttribute("basketItems", basketItemService.getBasketItems(authentication));
    }

    @Override
    public void populateOrderHistoryModel(Model model, Authentication authentication) {
        model.addAttribute("orders", orderService.historyOrders(authentication));
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));
    }

    @Override
    public String summaryView(Model model, Authentication authentication) {
        if (basketInfoService.isBasketEmpty(authentication)) {
            handleEmptyBasket(model, authentication);
            return "basket";
        }
        populateSummaryModel(model, authentication);
        return "summary";
    }

    private void handleEmptyBasket(Model model, Authentication authentication) {
        model.addAttribute("sum", ZERO_ITEMS_IN_BASKET);
        model.addAttribute("quantity", ZERO_ITEMS_IN_BASKET);
        model.addAttribute("basketIsEmpty", "Basket is empty!");
    }

    private void populateSummaryModel(Model model, Authentication authentication) {
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("basketItems", basketItemService.getBasketItems(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));

        UserDto currentUser = userService.getCurrentUser(authentication);
        model.addAttribute("name", currentUser.getFirstName());
        model.addAttribute("surname", currentUser.getLastName());
        model.addAttribute("orderDto", new OrderDto());
    }
}
