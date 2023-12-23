package com.rafal.IStore.item.controller;

import com.rafal.IStore.item.ItemViewService;
import com.rafal.IStore.shoppingbasket.BasketOperation;
import com.rafal.IStore.shoppingbasket.BasketService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sneaker-store")
public class ItemController {

    private final BasketService basketService;
    private final ItemViewService itemViewService;

    public ItemController(BasketService basketService, ItemViewService itemViewService) {
        this.basketService = basketService;
        this.itemViewService = itemViewService;
    }

    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        itemViewService.populateItemsModel(model, authentication);
        return itemViewService.correctViewDueToRole(model, authentication);
    }

    @PostMapping("/add/{itemId}")
    public String addItemToBasket(@PathVariable("itemId") Long itemId, @RequestParam("size") int selectedSize, Model model, Authentication authentication) {
        basketService.itemOperation(itemId, selectedSize, BasketOperation.INCREASE, authentication);
        itemViewService.populateItemsModel(model, authentication);
        return "redirect:/sneaker-store/home";
    }
}
