package com.rafal.IStore.controller;

import com.rafal.IStore.dto.OrderDto;
import com.rafal.IStore.service.basket.BasketInfoService;
import com.rafal.IStore.service.basket.BasketItemService;
import com.rafal.IStore.service.basket.BasketService;
import com.rafal.IStore.service.item.ItemService;
import com.rafal.IStore.service.order.OrderService;
import com.rafal.IStore.service.order.OrderViewService;
import com.rafal.IStore.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sneaker-store/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderViewService orderViewService;
    private final BasketInfoService basketInfoService;

    public OrderController(BasketService basketService, OrderService orderService, OrderViewService orderViewService, UserService userService, ItemService itemService, BasketItemService basketItemService, BasketInfoService basketInfoService) {
        this.orderService = orderService;
        this.orderViewService = orderViewService;
        this.basketInfoService = basketInfoService;
    }

    @GetMapping("/basket")
    public String showBasket(Model model, Authentication authentication) {
        orderViewService.populateSumQuantityAndBasketItems(model, authentication);
        return "basket";
    }

    @GetMapping("/summary")
    public String showSummary(Model model, Authentication authentication) {
        if (basketInfoService.isBasketEmpty(authentication)) {
            orderViewService.handleEmptyBasket(model, authentication);
            return "basket";
        }
        orderViewService.populateSummaryModel(model, authentication);
        return "summary";
    }

    @PostMapping("/save-order")
    public String saveOrder(@Valid OrderDto orderDto,
                            BindingResult bindingResult,
                            Model model,
                            Authentication authentication) {
        if (bindingResult.hasErrors()) {
            orderViewService.populateSumQuantityAndBasketItems(model, authentication);
            return "summary";
        }
        orderService.saveOrder(orderDto, authentication);
        return "thanks-for-shopping";
    }

    @GetMapping("/history")
    public String orderHistory(Model model, Authentication authentication) {
        orderViewService.populateOrderHistoryModel(model, authentication);
        return "orders";
    }
}
