package com.rafal.IStore.service.item;

import com.rafal.IStore.service.basket.BasketInfoService;
import com.rafal.IStore.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ItemViewServiceImpl implements ItemViewService{
    private final BasketInfoService basketInfoService;
    private final UserService userService;
    private final ItemService itemService;

    public ItemViewServiceImpl(BasketInfoService basketInfoService, UserService userService, ItemService itemService) {
        this.basketInfoService = basketInfoService;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public void populateUserInfoModel(Model model, Authentication authentication) {
        model.addAttribute("sum", basketInfoService.getSum(authentication));
        model.addAttribute("quantity", basketInfoService.getQuantity(authentication));
        model.addAttribute("firstName", userService.getCurrentUser(authentication).getFirstName());
    }

    @Override
    public void populateItemsModel(Model model, Authentication authentication) {
        model.addAttribute("items", itemService.getAllItems());
    }
}
