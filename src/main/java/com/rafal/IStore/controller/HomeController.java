package com.rafal.IStore.controller;

import com.rafal.IStore.service.basket.BasketOperation;
import com.rafal.IStore.service.basket.BasketInfoService;
import com.rafal.IStore.service.basket.BasketService;
import com.rafal.IStore.service.item.ItemService;
import com.rafal.IStore.service.item.ItemViewService;
import com.rafal.IStore.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sneaker-store")
public class HomeController {

    private final BasketService basketService;
    private final UserService userService;
    private final ItemViewService itemViewService;

    public HomeController(BasketService basketService, UserService userService, ItemService itemService, BasketInfoService basketInfoService, ItemViewService itemViewService) {
        this.basketService = basketService;
        this.userService = userService;
        this.itemViewService = itemViewService;
    }

    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        itemViewService.populateItemsModel(model, authentication);
        if (userService.isAdmin(authentication)) {
            return "adminview/admin-home";
        }
        itemViewService.populateUserInfoModel(model, authentication);
        return "home";
    }

    @PostMapping("/add/{itemId}")
    public String addItemToBasket(@PathVariable("itemId") Long itemId,
                                  @RequestParam("size") int selectedSize,
                                  Model model,
                                  Authentication authentication) {
        basketService.itemOperation(itemId, selectedSize, BasketOperation.INCREASE, authentication);
        itemViewService.populateItemsModel(model, authentication);
        return "redirect:/sneaker-store/home";
    }
}
