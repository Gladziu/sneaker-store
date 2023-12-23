package com.rafal.IStore.shoppingbasket.controller;

import com.rafal.IStore.order.OrderViewService;
import com.rafal.IStore.shoppingbasket.BasketOperation;
import com.rafal.IStore.shoppingbasket.BasketService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sneaker-store/order")
public class BasketController {
    private final String REDIRECT_TO_BASKET_PAGE = "redirect:/sneaker-store/order/basket";
    private final BasketService basketService;
    private final OrderViewService orderViewService;
    public BasketController(BasketService basketService, OrderViewService orderViewService) {
        this.basketService = basketService;
        this.orderViewService = orderViewService;
    }

    @GetMapping("/basket")
    public String basketPage(Model model, Authentication authentication) {
        orderViewService.populateSumQuantityAndBasketItems(model, authentication);
        return "basket";
    }

    @GetMapping("/increase/{itemId}/{size}")
    public String increaseItem(@PathVariable("itemId") Long itemId, @PathVariable("size") int size, Authentication authentication) {
        basketService.itemOperation(itemId, size, BasketOperation.INCREASE, authentication);
        return REDIRECT_TO_BASKET_PAGE;
    }

    @GetMapping("/decrease/{itemId}/{size}")
    public String decreaseItem(@PathVariable("itemId") Long itemId, @PathVariable("size") int size, Authentication authentication) {
        basketService.itemOperation(itemId, size, BasketOperation.DECREASE, authentication);
        return REDIRECT_TO_BASKET_PAGE;
    }

    @GetMapping("/remove/{itemId}/{size}")
    public String removeItemFromBasket(@PathVariable("itemId") Long itemId, @PathVariable("size") int size, Authentication authentication) {
        basketService.itemOperation(itemId, size, BasketOperation.REMOVE, authentication);
        return REDIRECT_TO_BASKET_PAGE;
    }
}
